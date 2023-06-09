#define IR_SEND_PIN 3

#include <SPI.h>

#include <Ethernet.h>

#include <aREST.h>

#include <avr/wdt.h>

#include <IRremote.hpp>

byte mac[] = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
IPAddress ip(000, 000, 0, 000);
IPAddress gateway(192, 168, 0, 1);
IPAddress subnet(255, 255, 255, 0);
EthernetServer server(8080);

aREST rest = aREST();

int estadoAr(String command);
int temperaturaAr(String command);

void setup(void) {

  Serial.begin(9600);

  rest.set_id("001");
  rest.set_name("ar_condicionado");
  
  rest.function("estado",estadoAr);
  rest.function("temperatura",temperaturaAr);

  Ethernet.begin(mac, ip);

  server.begin();
//  Serial.print("server is at ");
//  Serial.println(Ethernet.localIP());

  wdt_enable(WDTO_4S);
}

void loop() {

  EthernetClient client = server.available();
  rest.handle(client);
  wdt_reset();
}

int estadoAr(String command) {

  int state = command.toInt();

  if (state == HIGH) {

    uint32_t tRawData[] = {0x7B56, 0x1A10, 0x1C000000, 0x4B0A0B};
    IrSender.sendPulseDistanceWidthFromArray(38, 8300, 4150, 550, 1550, 550, 500, & tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 2);
  Serial.println("ligoou");

  } else {

    uint32_t tRawData[] = {0x7B56, 0xC010, 0x1A000000, 0x4A0A0B};
    IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4050, 600, 1500, 600, 500, & tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }

  return state;
}

int temperaturaAr(String command) {

  int temperatura = command.toInt();

  if (temperatura == 17) {

    uint32_t tRawData[]={0x6D56, 0x1A20, 0x1C000000, 0x4F0B1B};
    IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4100, 550, 1550, 550, 550, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);
  }
  
  if (temperatura == 18) {

    uint32_t tRawData[]={0x6E56, 0x1A20, 0x1D000000, 0x510B1B};
    IrSender.sendPulseDistanceWidthFromArray(38, 8350, 4150, 600, 1550, 600, 500, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);
  }

  if (temperatura == 19) {

    uint32_t tRawData[]={0x6F56, 0x1A20, 0x1E000000, 0x530B1B};
    IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4100, 550, 1550, 550, 550, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }

  if (temperatura == 20) {

    uint32_t tRawData[]={0x7056, 0x1A20, 0x20000000, 0x380B1B};
    IrSender.sendPulseDistanceWidthFromArray(38, 8350, 4150, 550, 1550, 550, 500, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);
  }

  return temperatura;
}
