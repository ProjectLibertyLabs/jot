package com.method5.jot.extrinsic;

import com.method5.jot.entity.MultiSignature;
import com.method5.jot.events.EventRecord;
import com.method5.jot.extrinsic.call.Call;
import com.method5.jot.extrinsic.model.AddItemAction;
import com.method5.jot.extrinsic.model.ItemAction;
import com.method5.jot.extrinsic.model.ItemizedSignaturePayloadV2;
import com.method5.jot.query.model.AccountId;
import com.method5.jot.query.model.ItemizedStoragePageResponse;
import com.method5.jot.rpc.PolkadotWs;
import com.method5.jot.signing.SigningProvider;
import com.method5.jot.util.HexUtil;
import com.method5.jot.wallet.Wallet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Testcontainers
public class E2ETest {
  public final static String FREQUENCY_VERSION = "v1.17.5";

  @Container
  public static GenericContainer<?> frequencyTestContainer = new GenericContainer<>(DockerImageName.parse(String.format("frequencychain/standalone-node:%s", FREQUENCY_VERSION)))
      .withExposedPorts(30333, 9944, 9933)
      .waitingFor(org.testcontainers.containers.wait.strategy.Wait.forLogMessage(".*Running JSON-RPC server.*", 1));

  @BeforeAll
  public static void setup() { frequencyTestContainer.start(); }

  public static String getWsAddress() {
    return String.format("ws://%s:%s", frequencyTestContainer.getHost(), frequencyTestContainer.getMappedPort(9944));
  }

  @Test
  public void msaTest() {
    try(PolkadotWs api = new PolkadotWs(getWsAddress())) {
      String chain = api.query().system().chain();
      Assertions.assertEquals("Frequency Development (No Relay)", chain);

      Wallet alice = Wallet.fromSr25519Seed(HexUtil.hexToBytes("0xe5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a"));
      Assertions.assertEquals("5GrwvaEF5zXb26Fz9rcQpDWS57CtERHpNehXCPcNoHGKutQY", alice.getAddress(42));

      SigningProvider aliceSigningProvider = alice.getSigner();

      Call call = api.tx().msa().createMsa();

      ExtrinsicResult result = call.signAndWaitForResults(aliceSigningProvider);

      List<EventRecord> eventRecordList = result.getEvents();
      for (EventRecord eventRecord : eventRecordList) {
        System.out.println("Event: " + eventRecord.method());
      }

      EventRecord msaCreated = eventRecordList.stream().filter(r -> Objects.equals(r.method(), "MsaCreated")).toList().getFirst();
      Assertions.assertNotNull(msaCreated);

      String hash = result.getHash();

      System.out.println("Extrinsic hash: " + hash);

      ExtrinsicResult failure = call.signAndWaitForResults(aliceSigningProvider);

      System.out.println(failure.getError().toHuman());
      Assertions.assertEquals("Module[60] Error[0]: KeyAlreadyRegistered", failure.getError().toHuman());

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void createSchemaV3Test() {
    try(PolkadotWs api = new PolkadotWs(getWsAddress())) {
      String chain = api.query().system().chain();
      Assertions.assertEquals("Frequency Development (No Relay)", chain);

      Wallet alice = Wallet.fromSr25519Seed(HexUtil.hexToBytes("0xe5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a"));
      Assertions.assertEquals("5GrwvaEF5zXb26Fz9rcQpDWS57CtERHpNehXCPcNoHGKutQY", alice.getAddress(42));

      SigningProvider aliceSigningProvider = alice.getSigner();

      Call msaCall = api.tx().msa().createMsa();

      msaCall.signAndWaitForResults(aliceSigningProvider);

      String itemizedSchemaModel = "{\"key1\":1, \"key2\":2}";
      List<Integer> schemasSettings = List.of( 1);
      Call createSchemaV3Call = api.tx().schemas().createSchemaV3(
          itemizedSchemaModel,
          (byte) 0,
          (byte) 2,
          schemasSettings,
"dsnp.public-follow"
      );

      System.out.println("Call data: " + HexUtil.bytesToHex(createSchemaV3Call.callData()));

      ExtrinsicResult result = createSchemaV3Call.signAndWaitForResults(aliceSigningProvider);

      List<EventRecord> eventRecordList = result.getEvents();
      for (EventRecord eventRecord : eventRecordList) {
        System.out.println("Event: " + eventRecord.method());
        System.out.println("Schema: " + eventRecord.attributes().toString());
      }

      ItemizedStoragePageResponse result1 = api.query().statefulStorage().getItemizedStorage(BigInteger.ONE, 16001);

      System.out.println(result1.toString());

      //TODO: Now add an item in there, then check again if there's storage there
      byte[] payload = new byte[] { 0x48, 101, 108, 108, 111 };
      System.out.println("payload to hex" + HexUtil.bytesToHex(payload));
      List<ItemAction> actions = List.of(new AddItemAction(payload));
      ItemizedSignaturePayloadV2 itemizedPayload = new ItemizedSignaturePayloadV2(16001, BigInteger.ZERO, 200L, actions);

      Call applyItemsCall = api.tx().statefulStorage().applyItemActionsWithSignatureV2(
          AccountId.fromSS58(alice.getAddress()),
          (byte) 1,
          HexUtil.hexToBytes("0xd61ec2e33007b05cc6bf3fb8d9674bfe1662403a031fc28d4c90650c221e221328e9c4a65093df163d96b251afa1a0aa0b1b4157ebf032975ca89a84aca44488"),
          itemizedPayload
      );

      System.out.println("Apply Items Call Data: " + HexUtil.bytesToHex(applyItemsCall.callData()));

      ExtrinsicResult applyItemsResult = applyItemsCall.signAndWaitForResults(aliceSigningProvider);
      List<EventRecord> applyItemsEventRecordList = applyItemsResult.getEvents();
      for (EventRecord eventRecord : applyItemsEventRecordList) {
        System.out.println("Event: " + eventRecord.method());
        System.out.println("Schema: " + eventRecord.attributes().toString());
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @AfterAll
  public static void teardown() {
    frequencyTestContainer.stop();
  }
}
