package com.custom.cache.controller;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import com.custom.cache.service.StreetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/street")
@Api(value = "StreetController")
public class StreetController {

    private final StreetService streetService;

    @GetMapping("/coordinate/{streetName}")
    @ApiOperation(value = "Shows street coordinate.",
            response = ResponseEntity.class,
            authorizations = @Authorization("Authorization"))
    public ResponseEntity<Integer> subscriptionsYPerson(
            @ApiParam(value = "The name of the street whose coordinate you need",
                    required = true)
            @PathVariable String streetName) {
        Integer coordinate = streetService.getCoordinate(streetName);
        return ResponseEntity.ok(coordinate);
    }
}
