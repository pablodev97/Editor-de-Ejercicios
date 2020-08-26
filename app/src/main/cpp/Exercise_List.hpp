//
// Created by PcCom on 04/06/2020.
//

#ifndef EXERCISEEDITORTOOL_EXERCISE_LIST_HPP
#define EXERCISEEDITORTOOL_EXERCISE_LIST_HPP

#include <vector>

#include "nlohmann/json.hpp"
#include "Exercise.hpp"

class Exercise_List
{
public:

    static Exercise_List & instance ()
    {
        static Exercise_List controller;
        return controller;
    }

private:

    /**
     * @brief Vector con todos los ejercicios.
     */
    std::vector<Exercise> exercises;

    /**
     * @brief Archivo json de los ejercicios.
     */
    nlohmann::json exercises_json;

    /**
     * @brief Variable usada para debuggear valores del json.
     */
    std::string debug;

public:

    /**
     * @brief Función de carga del archivo json.
     * @param path Ruta de carga.
     */
    void load_json(const std::string & path);

    /**
     * @brief Getter de los ejercicios.
     * @return exercises
     */
    std::vector<Exercise> get_exercises();

    /**
     * Función que añade un nueve ejercicio al vector exercises y actualiza el json.
     * @param exercise Nuevo ejercicio.
     */
    void add_exercise (Exercise exercise);

    /**
     * @brief Función que edita un ejercicio del vector dada su posición.
     * @param exercise
     * @param position
     */
    void edit_exercise (Exercise exercise, int position);

    /**
     * @brief Función que elimina el ejercicio de la posición indicada.
     * @param position
     */
    void delete_exercise (int position);

    /**
     * @brief Función que actualiza los valores del json.
     */
    void save_json();

    /**
     * Función que devuelve el valor en string del json.
     * @return
     */
    std::string print_json();

};

#endif //EXERCISEEDITORTOOL_EXERCISE_LIST_HPP
