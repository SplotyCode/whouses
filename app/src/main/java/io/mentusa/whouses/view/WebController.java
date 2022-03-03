package io.mentusa.whouses.view;

import io.mentusa.whouses.access.Access;
import io.mentusa.whouses.psi.ElementRepository;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @GetMapping(value = "/api/query", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Access> index(@RequestParam("input") String input) {
        return ElementRepository.INSTANCE.whoUses(input);
    }

    @GetMapping(value = "/api/autocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocomplete(@RequestParam("input") String input) {
        return ElementRepository.INSTANCE.startsWith(input);
    }
}
