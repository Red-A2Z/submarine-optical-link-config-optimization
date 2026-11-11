package org.example.submarineopticallinkconfigoptimization.repository;


import org.example.submarineopticallinkconfigoptimization.dto.LinkUpdateRequestDTO;
import org.example.submarineopticallinkconfigoptimization.entity.Link;
import org.example.submarineopticallinkconfigoptimization.exception.RepositoryException;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LinkRepository {


    private final String url = "jdbc:postgresql://localhost:5432/link_db";
    private final String username = "postgres";
    private final String password = "123456";



    public void createLink(Link link){

        String sql = """
        INSERT INTO link(
        linkName,linkDescription,linkLength,linkAttenuation,
        transmitterOutputPower,minReceivedPower,minAmplifierGain,maxAmplifierGain,
        minAmplifierInputPower,maxAmplifierOutputPower,maxNumberOfAmplifiers,numberOfAmplifiers,
        distanceBetweenAmplifiers,amplifierGain,amplifierInputPower,finalReceivedPower
        )
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);
        """;

        try(
                Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
                ){

            preparedStatement.setString(1,link.getLinkName());
            preparedStatement.setString(2,link.getLinkDescription());
            preparedStatement.setDouble(3,link.getLinkLength());
            preparedStatement.setDouble(4,link.getLinkAttenuation());

            preparedStatement.setDouble(5,link.getTransmitterOutputPower());
            preparedStatement.setDouble(6,link.getMinReceivedPower());
            preparedStatement.setDouble(7,link.getMinAmplifierGain());
            preparedStatement.setDouble(8,link.getMaxAmplifierGain());

            preparedStatement.setDouble(9,link.getMinAmplifierInputPower());
            preparedStatement.setDouble(10,link.getMaxAmplifierOutputPower());
            preparedStatement.setInt(11,link.getMaxNumberOfAmplifiers());
            preparedStatement.setInt(12,link.getNumberOfAmplifiers());

            preparedStatement.setDouble(13,link.getDistanceBetweenAmplifiers());
            preparedStatement.setDouble(14,link.getAmplifierGain());
            preparedStatement.setDouble(15,link.getAmplifierInputPower());
            preparedStatement.setDouble(16,link.getFinalReceivedPower());


            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected != 1 ){
                throw new RepositoryException("Unsuccessful single-row insert");
            }

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){

                if(generatedKeys.next()){
                    link.setId(generatedKeys.getLong(1));
                }
                else{
                    throw new RepositoryException("Link ID was not set");
                }
            }

        } catch (SQLException e) {
            throw new RepositoryException("An error has occurred while trying to create a link record in the db");
        }

    }





    public List<Link> getAllRecords(){

        List<Link> records = new ArrayList<>();

        String sql = "SELECT * FROM link;";

        try(
                Connection connection = DriverManager.getConnection(url,username,password);
                Statement statement = connection.createStatement();
                ResultSet resultSet =  statement.executeQuery(sql);
        ){

            while(resultSet.next()){
                records.add(mapToEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new RepositoryException("An error has occurred while trying to get all link records from the db");
        }

        return records;
    }



    private Link mapToEntity(ResultSet resultSet) throws SQLException {

        Link link = new Link();

        link.setId(resultSet.getLong(1));
        link.setLinkName(resultSet.getString(2));
        link.setLinkDescription(resultSet.getString(3));
        link.setLinkLength(resultSet.getDouble(4));
        link.setLinkAttenuation(resultSet.getDouble(5));
        link.setTransmitterOutputPower(resultSet.getDouble(6));
        link.setMinReceivedPower(resultSet.getDouble(7));
        link.setMinAmplifierGain(resultSet.getDouble(8));
        link.setMaxAmplifierGain(resultSet.getDouble(9));
        link.setMinAmplifierInputPower(resultSet.getDouble(10));
        link.setMaxAmplifierOutputPower(resultSet.getDouble(11));
        link.setMaxNumberOfAmplifiers(resultSet.getInt(12));
        link.setNumberOfAmplifiers(resultSet.getInt(13));
        link.setDistanceBetweenAmplifiers(resultSet.getDouble(14));
        link.setAmplifierGain(resultSet.getDouble(15));
        link.setAmplifierInputPower(resultSet.getDouble(16));
        link.setFinalReceivedPower(resultSet.getDouble(17));

        return link;

    }



    public void updateLinkNameDescription(long id, String newName, String newDescription){
        String sql = "";


        if (newName!=null && newDescription!=null){
            sql = """
                    UPDATE link
                    SET linkName=?, linkDescription=?
                    WHERE id=?
                    """;
        } else if (newName==null && newDescription==null) {
            return;
            
        } else if (newName!=null) {
            sql = """
                    UPDATE link
                    SET linkName=?
                    WHERE id=?
                    """;

        } else{
            sql = """
                    UPDATE link
                    SET linkDescription=?
                    WHERE id=?
                    """;
        }


            try(

                Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){


                if (newName!=null && newDescription!=null){
                    preparedStatement.setString(1,newName);
                    preparedStatement.setString(2,newDescription);
                    preparedStatement.setLong(3,id);

                } else if (newDescription==null) {
                    preparedStatement.setString(1,newName);
                    preparedStatement.setLong(2,id);

                } else{
                    preparedStatement.setString(1,newDescription);
                    preparedStatement.setLong(2,id);
                }

                int rowsAffected = preparedStatement.executeUpdate();

                if(rowsAffected!=1){
                    throw new RepositoryException("Unsuccessful single-row update");
                }


        } catch (SQLException e) {
            throw new RepositoryException("A DB error has occurred while trying to update a link");
        }


    }



    public void deleteLink(long linkId){

        String sql = "DELETE from link WHERE id =?";


        try(
                Connection connection = DriverManager.getConnection(url,username,password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){

            preparedStatement.setLong(1,linkId);

            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected!=1){
                throw new RepositoryException("Unsuccessful single-row delete");
            }



        }catch (SQLException e){
            throw new RepositoryException("A DB error has occurred while trying to delete a link");
        }

    }













}
