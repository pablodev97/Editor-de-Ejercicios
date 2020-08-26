//
// Created by PcCom on 04/06/2020.
//

#ifndef EXERCISEEDITORTOOL_EXERCISE_CPP
#define EXERCISEEDITORTOOL_EXERCISE_CPP

#include "Exercise.hpp"
#include <sstream>

Exercise::Exercise(std::string nombre, std::string descripcion, std::string url_video_tutorial, std::string musculo)
    : nombre (nombre), descripcion(descripcion), url_video_tutorial(url_video_tutorial), musculo(musculo)
{
}

void Exercise::set_nombre(std::string nombre) { this->nombre = nombre; }

std::string Exercise::get_nombre() { return this->nombre; }

void Exercise::set_descripcion(std::string descripcion) { this->descripcion = descripcion; }

std::string Exercise::get_descripcion() { return this->descripcion; }

void Exercise::set_url_video_tutorial(std::string url_video_tutorial) { this->url_video_tutorial = url_video_tutorial; }

std::string Exercise::get_url_video_tutorial() { return this->url_video_tutorial; }

void Exercise::set_musculo(std::string musculo) { this->musculo = musculo; }

std::string Exercise::get_musculo() { return this->musculo; }

#endif //EXERCISEEDITORTOOL_EXERCISE_CPP
