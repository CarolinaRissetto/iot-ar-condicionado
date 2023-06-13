#define IR_SEND_PIN 3

#include <SPI.h>

#include <Ethernet.h>

#include <aREST.h>

#include <avr/wdt.h>

#include <IRremote.hpp>

byte mac[] = {0xFC, 0x02, 0x96, 0x0F, 0xD5, 0xAC};
IPAddress ip(192, 168, 0, 112); //Deve ser alterado para o endereço de ip configurado no modem
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
  Serial.print("server is at ");
  Serial.println(Ethernet.localIP());

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

    uint32_t tRawData[]={0x7056, 0x1A20, 0x2A000000, 0x3C102E};
    IrSender.sendPulseDistanceWidthFromArray(38, 8350, 4100, 550, 1550, 550, 500, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);


  } else {

    uint32_t tRawData[]={0x7056, 0xC020, 0x33000000, 0x35102C};
    IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4050, 550, 1550, 550, 550, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }

  return state;
}

int temperaturaAr(String command) {

  int temperatura = command.toInt();

    Serial.println("temper");


  if (temperatura == 18) {

     uint32_t tRawData[]={0x6E56, 0x1A20, 0x13000000, 0x42102F};
     IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4050, 600, 1500, 600, 450, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }
  
  if (temperatura == 19) {

     uint32_t tRawData[]={0x6F56, 0x1A20, 0x9000000, 0x3A1030};
     IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4050, 550, 1550, 550, 550, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }

  if (temperatura == 20) {

   uint32_t tRawData[]={0x7056, 0x1A20, 0xC000000, 0x2F1030};
   IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4100, 600, 1500, 600, 500, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }

  if (temperatura == 21) {

   uint32_t tRawData[]={0x7156, 0x1A20, 0x15000000, 0x2B1031};
   IrSender.sendPulseDistanceWidthFromArray(38, 8400, 4050, 550, 1550, 550, 500, &tRawData[0], 120, PROTOCOL_IS_LSB_FIRST, 500, 1);

  }

  return temperatura;
}
