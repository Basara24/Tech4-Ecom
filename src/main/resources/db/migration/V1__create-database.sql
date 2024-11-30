CREATE TABLE Usuario (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Pedido (
    pedido_id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    data_pedido DATETIME NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(usuario_id)
);

CREATE TABLE Forma_Pgto (
    forma_pgto_id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE Pagamento (
    pagamento_id INT PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT NOT NULL,
    forma_pgto_id INT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedido(pedido_id),
    FOREIGN KEY (forma_pgto_id) REFERENCES Forma_Pgto(forma_pgto_id)
);

CREATE TABLE Item (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);

-- aqui ta a Chave composta como PRIMARY KEY
CREATE TABLE Item_Pedido (
    pedido_id INT NOT NULL,
    item_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (pedido_id, item_id), -- Chave primária composta
    FOREIGN KEY (pedido_id) REFERENCES Pedido(pedido_id),
    FOREIGN KEY (item_id) REFERENCES Item(item_id)
);

-- audit de alterações no preço dos itens usando a trigger
CREATE TABLE Audit_Preco_Item (
    audit_id INT PRIMARY KEY AUTO_INCREMENT,
    item_id INT NOT NULL,
    preco_antigo DECIMAL(10, 2) NOT NULL,
    preco_novo DECIMAL(10, 2) NOT NULL,
    data_alteracao DATETIME NOT NULL,
    FOREIGN KEY (item_id) REFERENCES Item(item_id)
);

-- trigger para auditar mudanças no preço dos itens
DELIMITER $$
CREATE TRIGGER AuditPrecoItem
AFTER UPDATE ON Item
FOR EACH ROW
BEGIN
    IF OLD.preco <> NEW.preco THEN
        INSERT INTO Audit_Preco_Item (item_id, preco_antigo, preco_novo, data_alteracao)
        VALUES (NEW.item_id, OLD.preco, NEW.preco, NOW());
    END IF;
END$$
DELIMITER ;