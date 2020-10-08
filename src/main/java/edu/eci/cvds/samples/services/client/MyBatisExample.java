/*
 * Copyright (C) 2020 danielhndz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cvds.samples.services.client;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author danielhndz
 */
public class MyBatisExample {

    /**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources.
     *
     * @return Instancia de SQLSessionFactory.
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory;
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejemplo de uso de MyBATIS.
     *
     * @param args Argumentos para el método, por ahora no se usan.
     */
    public static void main(String[] args) {
        SqlSessionFactory sessionFact = getSqlSessionFactory();
        SqlSession sqlSs = sessionFact.openSession();

        ClienteMapper clienteMapper = sqlSs.getMapper(ClienteMapper.class);
        ItemMapper itemMapper = sqlSs.getMapper(ItemMapper.class);
        TipoItemMapper tipoItemMapper = sqlSs.getMapper(TipoItemMapper.class);

        /**
        System.out.println("\n\n\n---------- ClienteDAO ----------");
        clientDAO(clienteMapper, 36);
        System.out.println("\n----------------------------------------");
        */

        /**
        System.out.println("\n\n\n---------- ItemDAO ----------");
        itemDAO(itemMapper, 17, 15L);
        System.out.println("\n----------------------------------------");
        */

        /**
        System.out.println("\n\n\n---------- TipoItemDAO ----------");
        tipoItemDAO(tipoItemMapper);
        System.out.println("\n----------------------------------------");
        */

        sqlSs.commit();
        sqlSs.close();
    }

    /**
     * Prueba los métodos de ClienteDAO usados por la implementación de ServiciosAlquiler.
     * Se veta el cliente con documento: documentClient - 1.
     *
     * @param clienteMapper El mapper de la clase Cliente.
     * @param documentClient El documento del nuevo cliente que se va a registrar.
     */
    private static void clientDAO(ClienteMapper clienteMapper, long documentClient) {
        System.out.println("\n-------------------- consultarCliente(docu: 1) --------------------");
        System.out.println(clienteMapper.consultarCliente(1));
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- consultarItemsCliente(docu: 1) --------------------");
        System.out.println(clienteMapper.consultarItemsCliente(1));
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- consultarClientes() --------------------");
        System.out.println(clienteMapper.consultarClientes());
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- agregarItemRentadoACliente(idCli: 1, idItm: 1) --------------------");
        clienteMapper.agregarItemRentadoACliente(1, 1, new Date(), new Date());
        System.out.println("----------------------------------------------------------------------");

        ArrayList<ItemRentado> list1 = new ArrayList<>();
        Cliente cliente = new Cliente(
                "Daniel",
                documentClient,
                "6788952",
                "KRA 109 #34 - C30",
                "daniel@protonmail.com",
                false,
                list1);
        System.out.println("\n-------------------- registrarCliente(  \n"+ cliente.toString() + "\n ) --------------------");
        clienteMapper.registrarCliente(cliente);
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- vetarCliente(docu: " + (documentClient-1) + ") --------------------");
        clienteMapper.vetarCliente(documentClient-1, true);
        System.out.println("----------------------------------------------------------------------");
    }

    /**
     * Prueba los métodos de ItemDAO usados por la implementación de ServiciosAlquiler.
     *
     * @param itemMapper El mapper de la clase Item.
     * @param itemId El id del nuevo ítem que se va a registrar.
     * @param tarifa La tarifa del ítem nuevo y la que se actualizará para el ítem 1.
     */
    private static void itemDAO(ItemMapper itemMapper, int itemId, long tarifa) {
        System.out.println("\n-------------------- valorMultaRetrasoPorDia(id: 1) --------------------");
        System.out.println(itemMapper.valorMultaRetrasoPorDia(1));
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- consultarItem(id: 1) --------------------");
        System.out.println(itemMapper.consultarItem(1));
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- consultarItemsDisponibles() --------------------");
        System.out.println(itemMapper.loadAvailable());
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- actualizarTarifaItem(id: 1, tarifa: " + tarifa + ") --------------------");
        itemMapper.actualizarTarifaItem(1, tarifa);
        System.out.println("----------------------------------------------------------------------");

        Item item = new Item(
                new TipoItem(1, "Videojuego"),
                itemId, "Juego", "Descripción W",
                new Date(), tarifa, "Formato W", "Género W"
        );
        System.out.println("\n-------------------- insertarItem( " + item.toString() + " ) --------------------");
        itemMapper.insertarItem(item);
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- consultarItems() --------------------");
        System.out.println(itemMapper.consultarItems());
        System.out.println("----------------------------------------------------------------------");
    }

    /**
     * Prueba los métodos de TipoItemDAO usados por la implementación de ServiciosAlquiler.
     *
     * @param tipoItemMapper El mapper de la clase TipoItem.
     */
    private static void tipoItemDAO(TipoItemMapper tipoItemMapper) {
        System.out.println("\n-------------------- consultarTipoItem(id: 1) --------------------");
        System.out.println(tipoItemMapper.getTipoItem(1));
        System.out.println("----------------------------------------------------------------------");

        System.out.println("\n-------------------- consultarTiposItem() --------------------");
        System.out.println(tipoItemMapper.getTiposItems());
        System.out.println("----------------------------------------------------------------------");
    }
}
