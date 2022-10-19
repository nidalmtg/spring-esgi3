package com.esgi.coursjava.projetapirest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.esgi.coursjava.projetapirest.models.Pokemon;

@RestController
public class PokemonController {

    private final AtomicLong counter = new AtomicLong();
    private List<Pokemon> pokemons = new ArrayList<>();

    PokemonController() {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/?limit=9&offset=0";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrl, String.class);

        // use jackson api to convert result to json
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(result);
            // make a for loop to get all the results
            System.out.println("start");
            ArrayList<String> urls = new ArrayList<>();
            for (JsonNode node : root.path("results")) {

                urls.add(node.path("url").asText());
            }
            for (String url : urls) {

                String result2 = restTemplate.getForObject(url, String.class);

                JsonNode pokemonJSON = mapper.readTree(result2);

                String name = pokemonJSON.path("name").asText();
                int hp = pokemonJSON.path("stats").path(0).path("base_stat").asInt();
                int attack = pokemonJSON.path("stats").path(1).path("base_stat").asInt();
                int defense = pokemonJSON.path("stats").path(2).path("base_stat").asInt();
                int atkSpe = pokemonJSON.path("stats").path(3).path("base_stat").asInt();
                int defSpe = pokemonJSON.path("stats").path(4).path("base_stat").asInt();
                int speed = pokemonJSON.path("stats").path(5).path("base_stat").asInt();

                pokemons.add(new Pokemon(counter.incrementAndGet(), name, hp, attack, defense, atkSpe, defSpe, speed));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("ready");

    }

    /**
     * Permet de récupérer l'ensemble des Pokemons
     * 
     * @return liste des pokemons
     */
    @RequestMapping("/")
    public List<Pokemon> getPokemon() {
        return pokemons.stream().collect(Collectors.toList());
    }

    /**
     * Permet de récupérer les infos d'un Pokemon en fonction de son id avec POST
     * 
     * @param id
     * @return le Pokemon selon son id
     */
    @RequestMapping(value = "/read", params = {}, method = RequestMethod.POST)
    public Pokemon readPokemon(@RequestBody Pokemon pokemon) {
        return pokemons.stream()
                .filter(p -> p.getId() == pokemon.getId())
                .findFirst()
                .orElse(null);
    }

    /**
     * Permet de récupérer la liste des Pokémons
     * 
     * @return l'ensemble des Pokemon
     */
    @RequestMapping(value = "/read", params = {}, method = RequestMethod.GET)
    public List<Pokemon> readPokemon() {
        return pokemons.stream().collect(Collectors.toList());
    }

    /**
     * Permet de récupérer les infos d'un Pokemon en fonction de son id avec GET
     * 
     * @param id
     * @return le Pokemon selon son id
     */
    @RequestMapping(value = "/read", params = { "id" }, method = RequestMethod.GET)
    public Pokemon readPokemon(@RequestParam(value = "id") long id) {
        return pokemons.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Permet d'insérer un pokemon dans la liste
     * 
     * @param name
     * @param getHp
     * @return le Pokemon inséré
     */
    @RequestMapping(value = "/create", params = {}, method = RequestMethod.POST)
    public Pokemon createPokemon(@RequestBody Pokemon pokemon) {

        Pokemon p = new Pokemon();
        p.setId(counter.incrementAndGet());

        System.out.println(pokemon.getName());

        if (readPokemon(pokemon.getId()) == null) {

            p.setName(pokemon.getName());
            p.setHp(pokemon.getHp());
            p.setAttack(pokemon.getAttack());
            p.setDefense(pokemon.getDefense());
            p.setAtkSpe(pokemon.getAtkSpe());
            p.setDefSpe(pokemon.getDefSpe());
            p.setSpeed(pokemon.getSpeed());
            pokemons.add(p);

        }

        return p;
    }

    /**
     * Permet de mettre à jour les infos d'un Pokemon en fonction de son id
     * 
     * @param id
     * @return le Pokemon mis à jour
     */
    @RequestMapping(value = "/update", params = {}, method = RequestMethod.PUT)
    public Pokemon updatePokemon(@RequestBody Pokemon pokemon) {
        return pokemons.stream()
                .filter(p -> p.getId() == pokemon.getId())
                .map((p) -> {
                    p.setName(pokemon.getName());
                    p.setHp(pokemon.getHp());
                    p.setAttack(pokemon.getAttack());
                    p.setDefense(pokemon.getDefense());
                    p.setAtkSpe(pokemon.getAtkSpe());
                    p.setDefSpe(pokemon.getDefSpe());
                    p.setSpeed(pokemon.getSpeed());
                    return p;
                })
                .collect(Collectors.toList()).get(0);
    }

    /**
     * Permet de supprimer un Pokemon en fonction de son id
     * 
     * @param id
     */
    @RequestMapping(value = "/delete", params = {}, method = RequestMethod.DELETE)
    public Pokemon deletePokemon(@RequestBody Pokemon pokemon) {
        Pokemon toDelete = pokemons.stream()
                .filter(p -> p.getId() == pokemon.getId())
                .collect(Collectors.toList())
                .get(0);

        pokemons.removeIf(p -> p.getId() == pokemon.getId());

        return toDelete;
    }

}
