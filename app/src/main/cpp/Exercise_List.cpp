//
// Created by PcCom on 04/06/2020.
//

#include "Exercise_List.hpp"
#include <fstream>

void Exercise_List::load_json(const std::string & path)
{
    std::ifstream ifs(path);

    if(ifs.is_open())
    {
        exercises_json = nlohmann::json::parse(ifs);
        
        debug = exercises_json.dump();

        for(auto & jj : exercises_json)
        {
            Exercise exercise (jj["ejercicio"], jj["descripcion"], jj["urlVideo"], jj["musculo"]);
            exercises.push_back(exercise);
        }
    }
    else
    {
        debug = "Failed";

        // Estos ejercicios los añado hardcodeados por el problema que tengo de ser incapaz de leer un
        // archivo de texto.
        Exercise ex1("ej1", "s jty dg", "url", "muscle");
        Exercise ex2("ej2", "s fgjgf dgh", "url", "muscle");
        Exercise ex3("ej3", "shgjtk yu kdg", "url", "muscle");

        exercises.push_back(ex1);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex2);
        exercises.push_back(ex3);

        for(auto & ex : exercises)
        {
            exercises_json.push_back({{"nombre", ex.get_nombre()}, {"descripcion", ex.get_descripcion()}, {"url", ex.get_url_video_tutorial()}, {"musculo", ex.get_musculo()}});
        }

    }
}

std::vector<Exercise> Exercise_List::get_exercises()
{
    return exercises;
}

void Exercise_List::add_exercise (Exercise exercise)
{
    exercises.push_back(exercise);
    save_json();
}

void Exercise_List::edit_exercise (Exercise exercise, int position)
{
    exercises.at(position) = exercise;
    save_json();
}

void Exercise_List::delete_exercise (int position)
{
    exercises.erase(exercises.begin() + position);
    save_json();
}

void Exercise_List::save_json()
{
    exercises_json.clear();

    for(auto & ex : exercises)
    {
        exercises_json.push_back({{"nombre", ex.get_nombre()}, {"descripcion", ex.get_descripcion()}, {"url", ex.get_url_video_tutorial()}, {"musculo", ex.get_musculo()}});
    }
}

std::string Exercise_List::print_json()
{
    return exercises_json.dump();
    //return debug;
}