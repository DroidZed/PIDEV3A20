/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.interfaces;

import java.util.List;

/**
 *
 * @author Aymen Dhahri
 * @param <T>
 */
public interface IService<T> {

   List<T> getAll();

   void update(T t, int id);

   void create(T t);
}
