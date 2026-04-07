# Locadora de Carros - Localiza Clone

## Descrição da Proposta
Este é um aplicativo Android desenvolvido para a disciplina de Desenvolvimento Mobile II. O objetivo é simular uma locadora de carros funcional, permitindo o cadastro de usuários, consulta de veículos disponíveis e simulação de aluguel com cálculo de diárias e taxas.

## Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **IDE:** Android Studio
- **Banco de Dados:** Room (Persistência Local SQLite)
- **UI:** XML Layouts, Material Design, View Binding
- **Concorrência:** Kotlin Coroutines
- **Controle de Versão:** Git & GitHub

## Arquitetura Adotada
O projeto segue o padrão de arquitetura modular para persistência:
- **Model:** Representação das entidades do banco de dados (Usuario, Carro, Locacao).
- **DAO (Data Access Object):** Interfaces de acesso aos dados.
- **Database:** Ponto central de acesso ao Room, com pré-população de dados.
- **Activities:** Camada de visualização e controle de eventos.

## Funcionalidades
- Login obrigatório para acesso.
- Cadastro de novos usuários.
- Listagem de carros (Onix, Corolla, Compass, Hilux, HB20).
- Cálculo automático de locação com base em datas (Diárias + Taxa de R$ 50,00).

## Instruções de Execução
1. Clone o repositório.
2. Abra no Android Studio.
3. Sincronize o Gradle.
4. Execute em um emulador com API 24+.

## Integrantes
- Martins Otávio
