
# iot-ar-condicionado

Um aplicativo para controle remoto de ar condicionado que se conecta a uma estrutura de Arduino e se comunica com o dispositivo de ar condicionado configurado. A aplicação permite o login e o cadastro de usuários, com serviço de autenticação e criptografia de senhas. Além disso, permite ligar, desligar, controlar a temperatura e agendar horários para ligar/desligar o dispositivo, bem como editar e excluir os agendamentos.

![login](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/e74f4697-2e96-47b6-8d85-2d637b7aa9b5)
![cadastro](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/067f89c7-7dbd-4ca8-86e5-4b433387cf27)
![cadastro erro](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/46dc5542-4438-4d20-91fd-476fc05ae3b8)
![controle](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/e047b142-0e9e-4eb0-842b-6f718445549f)
![agendamento](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/17531d9e-06fa-4849-8c45-85d170e3523e)

### Link para o video mostrando o funcionamento:

![video(https://github.com/CarolinaRissetto/iot-ar-condicionado/assets/65413041/cb88c4ba-f6f5-49bf-8b75-0cc4fcbdc9a4)](https://www.youtube.com/watch?v=Lycgs02nx9E)

# Instalação

 - É possivel fazer o download do aplicativo atravès do link: https://drive.google.com/file/d/10uOKwcrOzpMc0l8YlX43c_TJ94x2fBH7/view?usp=sharing
 
 - Para a instalação, é necessário fornecer todas as permissões necessárias.
 - É possível realizar o login com o seguinte usuário já registrado:
	 - Usuário: carolina.rissetto
	 - Senha: 123456
	 - Para o cadastro, é necessário informar o seu endereço IP público, que pode ser encontrado no seguinte site: https://meuip.com.br/

# Configurações

É fornecido o codigo fonte da aplicação, do aplicativo e do arduino.
Para automação de um aparelho novo é necessario  apenas alterar o codigo do arduino onde devera ser feita a construção da estrutura do arduino e algumas alterações no codigo do arduino. Será fornecido um passo a passo a seguir.

1- É necessário receber o sinal infravermelho dos botões do controle remoto e para isso a estrutura de arduino abaixo precisa ser construida.
![estrutura receiver utilizada com o codigo abaixo](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/7ad96745-0a82-4d41-993c-9b69c75df010)

2- Colando o codigo na arduino ide e subindo para placa ja é possivel captar o sinais infra vermelho como mostrado na imagem seguir:
![exemplo receiver serial monitor](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/b4a588a4-4607-4e74-b06b-252c85f2743b)
obs: Abrir o serial monitor onde serão exibidos os codigos necessarios para utilização.
os codigos recebidos para funcionamento do seu ar condicionado deverão substituir os disponiveis na meu codigo.

Essa é a estrutura final utilizada para se comunicar com o ar condicionado.
![estrutura sender utilizada no projeto](https://github.com/Arduino-IRremote/Arduino-IRremote/assets/65413041/e74f8054-d2e9-4776-af66-0e7c9efac574)

