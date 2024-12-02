CREATE PROCEDURE insert_massive_items(IN total INT)
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= total DO
        INSERT INTO Item (descricao, preco)
        VALUES (CONCAT('Item ', i), RAND() * 100);

        SET i = i + 1;
    END WHILE;
END;