# Sistema de Eventos

Este projeto é um sistema simples para gerenciamento de usuários e eventos, desenvolvido em Java. O sistema permite cadastro, remoção, busca e associação entre usuários e eventos, além de persistir os dados em arquivos de texto para manter as informações entre execuções.

---

## Funcionalidades

### Usuários
- Adicionar usuário (com suas informações)
- Remover usuário
- Buscar usuário por nome ou ID

### Eventos
- Adicionar evento (com suas informações)
- Remover evento
- Buscar evento por ID, nome, categoria ou cidade

### Participação
- Adicionar participação de usuários em eventos
- Remover participação de usuários em eventos

---

## Persistência de Dados

- Todos os dados são salvos em arquivos `.txt` localizados na pasta do programa.
- Ao iniciar o sistema, as informações são carregadas automaticamente desses arquivos.
- É necessário sair do programa utilizando a opção de sair do menu para que as alterações sejam salvas corretamente.

---

## Como usar

1. Clone este repositório
2. Compile o projeto com sua IDE ou via linha de comando
3. Execute o programa (Main.java)
4. Utilize o menu para adicionar, remover, buscar e gerenciar usuários e eventos
5. Para garantir que os dados sejam salvos, utilize a opção de sair do programa no menu principal

---

## Dependências

- Java 8 ou superior (devido ao uso de `LocalDateTime` e `DateTimeFormatter`)



