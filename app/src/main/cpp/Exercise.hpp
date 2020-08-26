//
// Created by PcCom on 04/06/2020.
//

#ifndef EXERCISEEDITORTOOL_EXERCISE_HPP
#define EXERCISEEDITORTOOL_EXERCISE_HPP

#include <string>
#include <nlohmann/json.hpp>

/**
 * @brief Clase con el modelo de datos de un ejercicio.
 */
class Exercise
{

private:

    /**
     * @brief Nombre del ejercicio.
     */
    std::string nombre;
    /**
     * @brief Descripción del ejercicio.
     */
    std::string descripcion;
    /**
     * @brief Url del video tutorial.
     */
    std::string url_video_tutorial;
    /**
     * @brief Músculo principal del ejercicio.
     */
    std::string musculo;

public:

    /**
     * @brief Constructor.
     */
    Exercise(std::string nombre, std::string descripcion, std::string url_video_tutorial, std::string musculo);

    void set_nombre(std::string nombre);

    std::string get_nombre();

    void set_descripcion(std::string descripcion);

    std::string get_descripcion();

    void set_url_video_tutorial(std::string url_video_tutorial);

    std::string get_url_video_tutorial();

    void set_musculo(std::string musculo);

    std::string get_musculo();

};

#endif //EXERCISEEDITORTOOL_EXERCISE_HPP
