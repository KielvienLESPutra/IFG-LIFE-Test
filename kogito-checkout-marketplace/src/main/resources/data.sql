TRUNCATE public.item;
ALTER SEQUENCE item_id_seq RESTART WITH 1;

INSERT INTO public.item
(created_by, created_datetime, updated_by, updated_datetime, description, "name", price, quantity)
VALUES('SYSTEM', NOW(), 'SYSTEM', NOW(), 'Buah Apel', 'Apel', 1000, 5);

INSERT INTO public.item
(created_by, created_datetime, updated_by, updated_datetime, description, "name", price, quantity)
VALUES('SYSTEM', NOW(), 'SYSTEM', NOW(), 'Buah Durian', 'Durian', 10000, 5);

INSERT INTO public.item
(created_by, created_datetime, updated_by, updated_datetime, description, "name", price, quantity)
VALUES('SYSTEM', NOW(), 'SYSTEM', NOW(), 'Buah Mangga', 'Mangga', 500, 10);

INSERT INTO public.item
(created_by, created_datetime, updated_by, updated_datetime, description, "name", price, quantity)
VALUES('SYSTEM', NOW(), 'SYSTEM', NOW(), 'Buah Jeruk', 'Jeruk', 100, 10);

INSERT INTO public.item
(created_by, created_datetime, updated_by, updated_datetime, description, "name", price, quantity)
VALUES('SYSTEM', NOW(), 'SYSTEM', NOW(), 'Buah Kelengkeng', 'Kelengkeng', 250, 5);