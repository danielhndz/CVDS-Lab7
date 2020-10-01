SELECT
c.nombre,
c.documento,
c.telefono,
c.direccion,
c.email,
c.vetado,
ir.id,
ir.fechainiciorenta,
ir.fechafinrenta,
i.id,
i.nombre,
i.descripcion,
i.fechalanzamiento,
i.tarifaxdia,
i.formatorenta,
i.genero,
ti.id,
ti.descripcion
FROM VI_CLIENTES AS c
LEFT JOIN VI_ITEMRENTADO AS ir ON c.documento = ir.CLIENTES_documento
LEFT JOIN VI_ITEMS AS i ON ir.ITEMS_id = i.id
LEFT JOIN VI_TIPOITEM AS ti ON i.TIPOITEM_id=ti.id;

SELECT
c.nombre,
c.documento,
c.telefono,
c.direccion,
c.email,
c.vetado,
ir.id,
ir.fechainiciorenta,
ir.fechafinrenta,
i.id,
i.nombre,
i.descripcion,
i.fechalanzamiento,
i.tarifaxdia,
i.formatorenta,
i.genero,
ti.id,
ti.descripcion
FROM VI_CLIENTES AS c
LEFT JOIN VI_ITEMRENTADO AS ir ON c.documento = ir.CLIENTES_documento
LEFT JOIN VI_ITEMS AS i ON ir.ITEMS_id = i.id
LEFT JOIN VI_TIPOITEM AS ti ON i.TIPOITEM_id=ti.id
WHERE c.documento = 1;

SELECT * FROM VI_ITEMS vi WHERE vi.id = 1;
