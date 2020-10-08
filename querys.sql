# id="consultarItemsRentados" resultMap="ItemRentadoResultMap"
SELECT
    ir.id,
    ir.fechainiciorenta,
    ir.fechafinrenta,
    i.id AS ITEMS_id,
    i.nombre AS ITEMS_nombre,
    i.descripcion AS ITEMS_descripcion,
    i.fechalanzamiento AS ITEMS_fechalanzamiento,
    i.tarifaxdia AS ITEMS_tarifaxdia,
    i.formatorenta AS ITEMS_formatorenta,
    i.genero AS ITEMS_genero,
    ti.id AS TIPOITEM_id,
    ti.descripcion AS TIPOITEM_descripcion
FROM VI_ITEMRENTADO AS ir
         LEFT JOIN VI_ITEMS AS i ON ir.ITEMS_id = i.id
         LEFT JOIN VI_TIPOITEM AS ti ON i.TIPOITEM_id = ti.id;
# ------------------------------------------------------------



# id="consultarItemsCliente" parameterType="long" resultMap="ItemRentadoResultMap"
SELECT
    ir.id,
    ir.fechainiciorenta,
    ir.fechafinrenta,
    i.id AS ITEMS_id,
    i.nombre AS ITEMS_nombre,
    i.descripcion AS ITEMS_descripcion,
    i.fechalanzamiento AS ITEMS_fechalanzamiento,
    i.tarifaxdia AS ITEMS_tarifaxdia,
    i.formatorenta AS ITEMS_formatorenta,
    i.genero AS ITEMS_genero,
    ti.id AS TIPOITEM_id,
    ti.descripcion AS TIPOITEM_descripcion
FROM
    VI_ITEMRENTADO AS ir
        LEFT JOIN
    VI_ITEMS AS i ON ir.ITEMS_id = i.id
        LEFT JOIN
    VI_TIPOITEM AS ti ON i.TIPOITEM_id = ti.id
WHERE
        ir.CLIENTES_documento = -1874919424;
# ------------------------------------------------------------



# id="consultarItems" resultMap="ItemResultMap"
SELECT
    i.id,
    i.nombre,
    i.descripcion,
    i.fechalanzamiento,
    i.tarifaxdia,
    i.formatorenta,
    i.genero,
    ti.id AS TIPOITEM_id,
    ti.descripcion AS TIPOITEM_descripcion
FROM
    VI_ITEMS AS i
        LEFT JOIN
    VI_TIPOITEM AS ti ON i.TIPOITEM_id = ti.id;
# ------------------------------------------------------------



# id="loadAvailable" resultMap="ItemResultMap"
SELECT
    i.id,
    i.nombre,
    i.descripcion,
    i.fechalanzamiento,
    i.tarifaxdia,
    i.formatorenta,
    i.genero,
    ti.id AS TIPOITEM_id,
    ti.descripcion AS TIPOITEM_descripcion
FROM
    VI_ITEMS AS i
        LEFT JOIN
    VI_TIPOITEM AS ti ON i.TIPOITEM_id = ti.id
        LEFT JOIN VI_ITEMRENTADO AS ir ON i.id = ir.ITEMS_id
WHERE
    ir.ITEMS_id IS NULL;
# ------------------------------------------------------------



# id="consultarItemRentado" resultMap="ItemRentadoResultMap" parameterType="int"
SELECT
    ir.id,
    ir.fechainiciorenta,
    ir.fechafinrenta,
    i.id AS ITEMS_id,
    i.nombre AS ITEMS_nombre,
    i.descripcion AS ITEMS_descripcion,
    i.fechalanzamiento AS ITEMS_fechalanzamiento,
    i.tarifaxdia AS ITEMS_tarifaxdia,
    i.formatorenta AS ITEMS_formatorenta,
    i.genero AS ITEMS_genero,
    ti.id AS TIPOITEM_id,
    ti.descripcion AS TIPOITEM_descripcion
FROM
    VI_ITEMRENTADO AS ir
        LEFT JOIN
    VI_ITEMS AS i ON ir.ITEMS_id = i.id
        LEFT JOIN
    VI_TIPOITEM AS ti ON i.TIPOITEM_id = ti.id
WHERE
        ir.id = 111;
# ------------------------------------------------------------



# id="consultarCliente" resultMap="ClienteResultMap" parameterType="long"
SELECT
    c.documento,
    c.nombre,
    c.telefono,
    c.direccion,
    c.email,
    c.vetado,
    ir.id AS ITEMRENTADO_id,
    ir.fechainiciorenta AS ITEMRENTADO_fechainiciorenta,
    ir.fechafinrenta AS ITEMRENTADO_fechafinrenta,
    i.id AS ITEMS_id,
    i.nombre AS ITEMS_nombre,
    i.descripcion AS ITEMS_descripcion,
    i.fechalanzamiento AS ITEMS_fechalanzamiento,
    i.tarifaxdia AS ITEMS_tarifaxdia,
    i.formatorenta AS ITEMS_formatorenta,
    i.genero AS ITEMS_genero,
    ti.id AS TIPOITEM_id,
    ti.descripcion AS TIPOITEM_descripcion
FROM
    VI_CLIENTES AS c
        LEFT JOIN
    VI_ITEMRENTADO AS ir ON c.documento = ir.CLIENTES_documento
        LEFT JOIN
    VI_ITEMS AS i ON ir.ITEMS_id = i.id
        LEFT JOIN
    VI_TIPOITEM AS ti ON i.TIPOITEM_id = ti.id
WHERE
        c.documento = 1;
# ------------------------------------------------------------

SELECT * FROM VI_CLIENTES;

SELECT * FROM VI_ITEMS;


SELECT * FROM VI_ITEMRENTADO;

SELECT * FROM VI_TIPOITEM;

UPDATE VI_ITEMS SET tarifaxdia = 12 WHERE id = 1;

SELECT
    items.*,
    VI_TIPOITEM.id AS tipoitem_id,
    VI_TIPOITEM.descripcion AS tipoitem_descripcion
FROM
    VI_ITEMS AS items
        JOIN
    VI_TIPOITEM
WHERE
        items.id = 1
GROUP BY items.id;
