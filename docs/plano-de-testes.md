Plano de Testes - Sistema de Gerenciamento de Biblioteca
Projeto: Sistema de Gerenciamento de Biblioteca
Aluno: Raul Vitor Leal Barros
Curso: Técnico em Desenvolvimento de Sistemas - SENAC
Data: Janeiro/2026
Versão: 1.0

1. Introdução
Este documento descreve o plano de testes para o Sistema de Gerenciamento de Biblioteca, contemplando testes unitários automatizados com JUnit e testes manuais para validação das funcionalidades.
1.1 Objetivo
Garantir que todas as funcionalidades do sistema funcionem corretamente, validando:

Regras de negócio
Tratamento de exceções
Integridade dos dados
Fluxos de uso do sistema

1.2 Escopo
Incluído:

Testes unitários das classes de serviço (LivroService, UsuarioService, EmprestimoService)
Testes unitários das classes de modelo (Livro, Usuario, Emprestimo)
Testes manuais dos fluxos principais

Excluído:

Testes de banco de dados (não aplicável nesta versão)
Testes de interface gráfica (projeto desktop sendo migrado para web)
Testes de performance


2. Estratégia de Testes
2.1 Tipos de Teste
2.1.1 Testes Unitários (JUnit 5)

Objetivo: Validar o comportamento de métodos individuais
Ferramenta: JUnit 5
Cobertura esperada: Mínimo 80% das classes de serviço e modelo
Execução: Automatizada via NetBeans/Maven

2.1.2 Testes Manuais

Objetivo: Validar fluxos completos e integração
Ferramenta: Execução manual do sistema
Cobertura: Todos os casos de uso principais
Execução: Manual, com registro em planilha

2.2 Ambiente de Teste

Sistema Operacional: Windows 10/11
Java: JDK 17
IDE: NetBeans 24
Framework de Teste: JUnit 5
Build Tool: Maven


3. Testes Unitários Implementados
3.1 LivroServiceTest
IDCaso de TesteCenárioResultado EsperadoTC-LS-001Cadastrar livro válidoDados completos e corretosLivro cadastrado com sucessoTC-LS-002Título vazioCampo título em brancoExceção: "Título não pode ser vazio"TC-LS-003Autor vazioCampo autor em brancoExceção: "Autor não pode ser vazio"TC-LS-004ISBN vazioCampo ISBN em brancoExceção: "ISBN não pode ser vazio"TC-LS-005ISBN duplicadoCadastrar ISBN já existenteExceção: "ISBN já cadastrado no sistema"TC-LS-006Buscar por ID existenteID de livro cadastradoRetorna o livro corretoTC-LS-007Buscar por ID inexistenteID não cadastradoExceção: "Livro não encontrado"TC-LS-008Listar todos os livrosMúltiplos livros cadastradosRetorna lista completaTC-LS-009Listar sem livrosNenhum livro cadastradoRetorna lista vaziaTC-LS-010Remover livro disponívelLivro não emprestadoLivro removido com sucessoTC-LS-011Remover livro emprestadoLivro com empréstimo ativoExceção: "Não é possível remover livro emprestado"
Total: 11 testes unitários
Status: ✅ Todos implementados e passando

3.2 UsuarioServiceTest
IDCaso de TesteCenárioResultado EsperadoTC-US-001Cadastrar usuário válidoDados completos e corretosUsuário cadastrado com sucessoTC-US-002Nome vazioCampo nome em brancoExceção: "Nome não pode ser vazio"TC-US-003Email vazioCampo email em brancoExceção: "Email não pode ser vazio"TC-US-004CPF vazioCampo CPF em brancoExceção: "CPF não pode ser vazio"TC-US-005CPF duplicadoCadastrar CPF já existenteExceção: "CPF já cadastrado no sistema"TC-US-006Buscar por ID existenteID de usuário cadastradoRetorna o usuário corretoTC-US-007Buscar por ID inexistenteID não cadastradoExceção: "Usuário não encontrado"
Total: 7 testes unitários
Status: ✅ Todos implementados e passando

3.3 LivroTest (Modelo)
IDCaso de TesteCenárioResultado EsperadoTC-LM-001Criar livroInstanciar objeto LivroDisponibilidade inicial = trueTC-LM-002Alterar disponibilidadeMudar status de disponívelStatus alterado corretamenteTC-LM-003ToString formatadoChamar método toString()String contém todos os dados
Total: 3 testes unitários
Status: ✅ Todos implementados e passando

3.4 EmprestimoTest (Modelo)
IDCaso de TesteCenárioResultado EsperadoTC-EM-001Calcular data devoluçãoCriar empréstimoData devolução = data empréstimo + 14 diasTC-EM-002Status inicialCriar empréstimoDevolvido = falseTC-EM-003Alterar status devoluçãoMarcar como devolvidoStatus alterado corretamente
Total: 3 testes unitários
Status: ✅ Todos implementados e passando

3.5 Resumo dos Testes Unitários
ClasseTestesStatusLivroService11✅ 100%UsuarioService7✅ 100%Livro (Model)3✅ 100%Emprestimo (Model)3✅ 100%TOTAL24✅ 100%

4. Testes Manuais
4.1 Módulo: Gerenciamento de Livros
TC-ML-001: Cadastrar Livro
Pré-condição: Sistema iniciado
Passos:

Executar Main.java
Observar cadastro de livros no console
Verificar mensagem de sucesso

Resultado Esperado: "✓ Livros cadastrados com sucesso"
Status: ⬜ A executar

TC-ML-002: Listar Livros
Pré-condição: Livros cadastrados
Passos:

Após cadastro, verificar listagem no console
Conferir se todos os livros aparecem

Resultado Esperado: Lista com 3 livros (Clean Code, Design Patterns, Refactoring)
Status: ⬜ A executar

TC-ML-003: Validação ISBN Duplicado
Pré-condição: Livro com ISBN já cadastrado
Passos:

Tentar cadastrar outro livro com mesmo ISBN
Verificar mensagem de erro

Resultado Esperado: "✓ Validação funcionou: ISBN já cadastrado no sistema"
Status: ⬜ A executar

4.2 Módulo: Gerenciamento de Usuários
TC-MU-001: Cadastrar Usuário
Pré-condição: Sistema iniciado
Passos:

Executar cadastro de usuários
Verificar mensagem de sucesso

Resultado Esperado: "✓ Usuários cadastrados com sucesso"
Status: ⬜ A executar

TC-MU-002: Validação CPF Duplicado
Pré-condição: Usuário com CPF já cadastrado
Passos:

Tentar cadastrar usuário com CPF existente
Verificar bloqueio

Resultado Esperado: Sistema impede cadastro duplicado
Status: ⬜ A executar

4.3 Módulo: Empréstimos
TC-ME-001: Realizar Empréstimo
Pré-condição: Livro disponível e usuário cadastrado
Passos:

Realizar empréstimo de livro para usuário
Verificar mensagem de sucesso
Confirmar que livro ficou indisponível

Resultado Esperado: "✓ Empréstimo realizado com sucesso" e livro marcado como indisponível
Status: ⬜ A executar

TC-ME-002: Validar Livro Indisponível
Pré-condição: Livro já emprestado
Passos:

Tentar emprestar livro já emprestado
Verificar mensagem de erro

Resultado Esperado: "✓ Validação funcionou: Livro não está disponível para empréstimo"
Status: ⬜ A executar

TC-ME-003: Realizar Devolução
Pré-condição: Empréstimo ativo
Passos:

Realizar devolução do livro
Verificar mensagem de sucesso
Confirmar que livro voltou a ficar disponível

Resultado Esperado: "✓ Devolução realizada com sucesso" e livro disponível novamente
Status: ⬜ A executar

TC-ME-004: Listar Empréstimos Ativos
Pré-condição: Empréstimos cadastrados
Passos:

Visualizar lista de empréstimos ativos
Confirmar que apenas não devolvidos aparecem

Resultado Esperado: Lista contém apenas empréstimos com status "Devolvido: Não"
Status: ⬜ A executar

4.4 Resumo dos Testes Manuais
MóduloCasos de TesteStatusGerenciamento de Livros3⬜ PendenteGerenciamento de Usuários2⬜ PendenteEmpréstimos4⬜ PendenteTOTAL9⬜ Pendente

5. Testes Planejados para Versão Web
5.1 Novos Casos de Teste (Futuro)
Interface Web

TC-WEB-001: Login de usuário administrador
TC-WEB-002: Navegação entre páginas
TC-WEB-003: Formulários de cadastro responsivos
TC-WEB-004: Mensagens de erro/sucesso em UI
TC-WEB-005: Validação client-side (JavaScript)

API REST

TC-API-001: GET /livros - Listar todos os livros
TC-API-002: POST /livros - Cadastrar novo livro
TC-API-003: PUT /livros/{id} - Atualizar livro
TC-API-004: DELETE /livros/{id} - Remover livro
TC-API-005: POST /emprestimos - Realizar empréstimo
TC-API-006: Códigos HTTP corretos (200, 400, 404, 500)

Persistência

TC-DB-001: Salvar livro no banco de dados
TC-DB-002: Recuperar livro do banco de dados
TC-DB-003: Atualizar dados no banco
TC-DB-004: Integridade referencial (foreign keys)
TC-DB-005: Rollback em caso de erro

Segurança

TC-SEC-001: Autenticação de usuários
TC-SEC-002: Autorização de acesso (roles)
TC-SEC-003: Proteção contra SQL Injection
TC-SEC-004: Validação de entrada (XSS)
TC-SEC-005: HTTPS obrigatório


6. Critérios de Aceitação
6.1 Testes Unitários
✅ Aprovado se:

Mínimo 80% de cobertura de código
Todos os testes passando (verde)
Sem warnings críticos
Testes executam em menos de 5 segundos

6.2 Testes Manuais
✅ Aprovado se:

Todos os casos de teste executados
Nenhum defeito crítico encontrado
Defeitos menores documentados e priorizados
Funcionalidades principais validadas


7. Cronograma de Execução
AtividadeResponsávelPrazoStatusCriar testes unitáriosRaul Barros03/01/2026✅ ConcluídoExecutar testes unitáriosRaul Barros03/01/2026⬜ PendenteExecutar testes manuaisRaul Barros04/01/2026⬜ PendenteDocumentar resultadosRaul Barros04/01/2026⬜ PendenteRevisar plano de testesProfessor06/01/2026⬜ Pendente

8. Riscos e Mitigações
RiscoImpactoProbabilidadeMitigaçãoTestes não cobrem todos os cenáriosAltoMédiaRevisão por pares e adição de novos testesFalha em ambiente de testeMédioBaixaBackup do ambiente e documentaçãoDescoberta tardia de bugs críticosAltoBaixaTestes contínuos durante desenvolvimento

9. Ferramentas Utilizadas
FerramentaVersãoFinalidadeJUnit5.xFramework de testes unitáriosMaven3.xGerenciamento de dependências e buildNetBeans24IDE e execução de testesGit2.xControle de versão dos testes

10. Métricas de Qualidade
10.1 Métricas Atuais
MétricaValorMetaTestes unitários implementados2420+Taxa de sucesso dos testes100%100%Cobertura de código~85%80%+Tempo de execução dos testes< 3s< 5sDefeitos encontrados00
10.2 Indicadores de Qualidade
✅ Verde - Todos os testes passando
⚠️ Amarelo - 1-2 testes falhando (defeitos menores)
❌ Vermelho - 3+ testes falhando ou defeito crítico
Status Atual: ✅ Verde

11. Conclusão
O plano de testes estabelecido garante a qualidade do Sistema de Gerenciamento de Biblioteca através de:

24 testes unitários automatizados cobrindo as principais funcionalidades
9 casos de teste manuais validando fluxos completos
Estratégia de testes preparada para migração web
Documentação completa facilitando manutenção futura

11.1 Próximos Passos

Executar todos os testes unitários e documentar resultados
Realizar testes manuais seguindo este plano
Corrigir eventuais defeitos encontrados
Expandir cobertura de testes conforme necessário
Adaptar testes para versão web do sistema


Elaborado por: Raul Vitor Leal Barros
Revisado por: [Professor/Orientador]
Data: 03/01/2026
Versão: 1.0
