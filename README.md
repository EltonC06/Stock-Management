# Stock Management

(https://github.com/EltonC06/Stock-Management/blob/main/LICENSE)

## Sobre o Projeto

Stock Management é uma aplicação back end construída para fins de aprendizado.

A aplicação permite ao usuário gerenciar suas ações, registrando investimentos iniciais e acompanhando o valor atual para calcular os ganhos.

## Tecnologias Utilizadas

### Back End
- Java
- MySQL

## Como Executar o Projeto

### Pré-requisitos
- IDE de Java (Preferencialmente Eclipse)
- Java 17
- MySQL

### Passo a Passo

1. **Clonar Repositório**
   ```bash
   git clone git@github.com:EltonC06/Stock-Management.git
   ```

2. **Configurar Banco de Dados**
   - Crie um banco de dados MySQL e configure as suas credenciais (URL do banco de dados, usuário, senha) no arquivo de configuração do projeto: db.properties.

3. **Entrar na Pasta do Projeto**
   - Abra o projeto em uma IDE Java (projeto testado somente no Eclipse).

4. **Executar o Programa**
   - Navegue até `src -> application -> Program` e execute o programa.

### Observações

- O programa foi testado apenas no Windows.
- Para modificar o local de armazenamento dos registros no arquivo csv, acesse:
  `src -> entities -> CsvLink` e altere o método `csvPathLink` com o caminho desejado.

## Como Você Pode Contribuir

- Melhorar a validação de entrada de dados para evitar erros.
- Adicionar funcionalidades de exportação de dados para CSV.
- Implementar uma interface gráfica para visualização das ações e ganhos.

## Autor

Elton da Costa Oliveira

[LinkedIn](https://www.linkedin.com/in/elton-da-costa/)
