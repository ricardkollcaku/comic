package com.senders.comic.domain.service;

import com.senders.comic.data.model.Comic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ComicService {
    @Autowired
    private XKCDComicService xkcdComicService;
    @Autowired
    private FeedBurnerService feedBurnerService;
    private final Integer numberOfComicsPerRequest;

    public ComicService(@Value("${url.element.number}") Integer numberOfComicsPerRequest) {
        this.numberOfComicsPerRequest = numberOfComicsPerRequest;
    }

    public Flux<Comic> getLastComics() {
        return Flux.merge(xkcdComicService.getTopComics(numberOfComicsPerRequest), feedBurnerService.getTopComics(numberOfComicsPerRequest))
                .sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

    }
}
