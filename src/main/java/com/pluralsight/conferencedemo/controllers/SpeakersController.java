package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakersRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakersRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakersRepository.getById(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakersRepository.saveAndFlush(speaker);
    }

    @PutMapping
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        //Because this is a PUT, we expect all attributes to be passed in. Otherwise they'll be NULL
        //If this is not what you want, you'll need Patch
        Speaker existingSpeaker = speakersRepository.getById(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakersRepository.saveAndFlush(speaker);
    }

    @DeleteMapping
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        //check for how to delete child records, it only deletes sessions
        speakersRepository.deleteById(id);
    }
}
