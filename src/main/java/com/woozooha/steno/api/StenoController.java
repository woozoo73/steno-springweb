package com.woozooha.steno.api;

import com.woozooha.steno.conf.Config;
import com.woozooha.steno.model.Story;
import com.woozooha.steno.model.StoryExample;
import com.woozooha.steno.writer.Reader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/steno-/stories/")
public class StenoController {

    @GetMapping
    public List<Story> getStories(StoryExample example) {
        Reader reader = Config.getCurrent().getReader();

        return reader.getStoryList(example);
    }

    @GetMapping("/{id}")
    public Story getStory(@PathVariable String id) {
        Reader reader = Config.getCurrent().getReader();

        return reader.getStory(id);
    }

}
