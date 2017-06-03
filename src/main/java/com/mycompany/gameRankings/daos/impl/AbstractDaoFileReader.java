package com.mycompany.gameRankings.daos.impl;

import com.mycompany.gameRankings.daos.GenericDao;
import com.mycompany.gameRankings.exceptions.ApplicationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaoFileReader<T> implements GenericDao<T> {

    public List<T> getAll() throws ApplicationException {
        List<T> objectList = new ArrayList<T>();

        try {
            BufferedReader bufferedReader = getBufferedReader();

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String [] data = line.split(" ");

                T objectToAdd = buildObject(data);

                objectList.add(objectToAdd);
            }

            bufferedReader.close();
        } catch (IOException e){
            throw new ApplicationException("Error retrieving objectList",e);
        }

        return objectList;
    }


    private BufferedReader getBufferedReader() throws FileNotFoundException {
        File file = new File(getFileLocation());

        FileInputStream fis = new FileInputStream(file);

        return new BufferedReader(new InputStreamReader(fis));
    }

    protected abstract String getFileLocation();

    protected abstract T buildObject(String[] data);

}
