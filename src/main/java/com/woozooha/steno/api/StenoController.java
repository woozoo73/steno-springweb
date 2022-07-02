package com.woozooha.steno.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozooha.steno.model.Story;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/steno-/stories/")
public class StenoController {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<Story> getStories() {
        Story story = new Story();
        File dayDir = story.getDateDir();
        if (!dayDir.exists()) {
            return Collections.emptyList();
        }

        File[] files = dayDir.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

        return Arrays.stream(files).map(f -> {
                            try {
                                return readStory(f);
                            } catch (Exception e) {
                                return null;
                            }
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Story getStory(@PathVariable String id) {
        List<Story> stories = this.getStories();

        return stories.stream().filter(s -> id.equals(s.getId())).findFirst().orElse(null);
    }

    @SneakyThrows
    private Story readStory(File file) {
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return objectMapper.readValue(reader, Story.class);
        }
    }

}
