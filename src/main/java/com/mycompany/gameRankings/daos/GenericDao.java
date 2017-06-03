package com.mycompany.gameRankings.daos;


import com.mycompany.gameRankings.exceptions.ApplicationException;

import java.util.List;

public interface GenericDao<T> {

    List<T> getAll() throws ApplicationException;

}
