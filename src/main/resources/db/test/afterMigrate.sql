truncate table estado restart identity cascade;
truncate table categoria restart identity cascade;
truncate table produto restart identity cascade;
truncate table cliente restart identity cascade;
truncate table funcao restart identity cascade;
truncate table endereco restart identity cascade;
truncate table pedido restart identity cascade;
truncate table item_pedido restart identity cascade;

insert into estado(nome) values
('Pernambuco'), ('São Paulo'), ('Rio de Janeiro');

insert into categoria(nome) values
('Pasteis'), ('Refrigerantes'), ('Bebidas Alcoólicas');

insert into produto(nome, preco, descricao, desconto, is_tem_desconto, is_tem_estoque, categoria_id) values
('Pastel de Frango', 4.99, 'O melhor pastel de frango da região', 0.0, 'FALSE', 'TRUE', 1),
('Pastel de Camarão', 6.99, 'O melhor pastel de camarão da região que possui camarão dentro do pastel!', 0.0, 'FALSE', 'TRUE', 1),
('Pastel de Queijo', 4.99, 'O melhor pastel de queijo da região', 0.0, 'FALSE', 'TRUE', 1),

('Coca Cola 2l', 7.99, 'Aquela coca cola para refrescar o seu dia!', 0.0, 'FALSE', 'TRUE', 2),
('Fanta 2l', 7.99, 'Guarana fanta sabor laranja!', 0.0, 'FALSE', 'TRUE', 2),
('Soda 2l', 7.99, 'Aquela soda refrescante!', 0.0, 'FALSE', 'TRUE', 2),

('Heineken 350ml', 4.99, 'Simplesmente a melhor cervejinha puro malte!', 0.0, 'FALSE', 'TRUE', 3),
('Johnie Walker Label 720ml', 179.99, 'O Whisky mais famoso do mundo com a mais pura qualidade que existe', 0.0, 'FALSE', 'TRUE', 3),
('Skol Puro Malte 450ml', 2.79, 'Aquele famosa Skol pae', 0.0, 'FALSE', 'TRUE', 3);

insert into cliente(nome, cpf, email, senha, telefone, is_conta_ativa) values
('João Andrade', '91189329085', 'john@gmail.com', '$2a$10$YXG.hySz34v/Vj5ak7KOGOBmi/TNMP.EWoJj8Nn/Z8OQbVLM9ZIhO', '81993332222', 'TRUE'),
('Leonardo Leitão', '09240454004', 'leo@gmail.com', '$2a$10$YXG.hySz34v/Vj5ak7KOGOBmi/TNMP.EWoJj8Nn/Z8OQbVLM9ZIhO', '81993332211', 'TRUE');

insert into funcao(cliente_id, funcoes) values
(1, 'ADMIN'), (1, 'CLIENTE'),
(2, 'CLIENTE');

insert into endereco (cep, endereco, bairro, complemento, numero, cidade, estado_id, cliente_id) values
('01001000', 'Praça da Sé', 'Sé', 'lado ímpar', '330', 'São Paulo', 2, 1),
('01001000', 'Praça da Sé', 'Sé', 'lado ímpar', '320', 'São Paulo', 2, 2),
('51020000', 'Avenida Boa Viagem', 'Boa Viagem', 'de 2176/2177 a 3077/3078', '220', 'Recife', 1, 1);

insert into pedido(data, situacao_pagamento, situacao_pedido, valor_total, cliente_id, endereco_de_entrega_id) values
('2021-05-09 12:00:00', 'PENDENTE', 'AGUARDANDO_PAGAMENTO', 21.97, 1, 3),
('2021-05-10 14:00:00', 'PENDENTE', 'AGUARDANDO_PAGAMENTO', 179.99, 1, 3);

insert into item_pedido (pedido_id, produto_id, desconto, quantidade, valor) values
(1, 2, 0, 2, 6.99), (1, 4, 0, 1, 7.99),
(2, 8, 0, 1, 179.99);