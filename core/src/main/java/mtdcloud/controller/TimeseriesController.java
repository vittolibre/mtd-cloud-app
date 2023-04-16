package mtdcloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mtdcloud.dto.TimeseriesDTO;
import mtdcloud.service.TimeseriesService;
import mtdcloud.vo.TimeseriesQueryVO;
import mtdcloud.vo.TimeseriesUpdateVO;
import mtdcloud.vo.TimeseriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "")
@Validated
@Controller
@RequestMapping("/timeseries")
public class TimeseriesController {

    @Autowired
    private TimeseriesService timeseriesService;

    @PostMapping
    @ApiOperation("Save ")
    public String save( @RequestBody TimeseriesVO vO) {
        return timeseriesService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete ")
    public void delete( @PathVariable("id") String id) {
        timeseriesService.delete(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update ")
    public void update( @PathVariable("id") String id,
                        @RequestBody TimeseriesUpdateVO vO) {
        timeseriesService.update(id, vO);
    }

    @GetMapping("/{id}")
    @ApiOperation("Retrieve by ID ")
    public TimeseriesDTO getById(@PathVariable("id") String id) {
        return timeseriesService.getById(id);
    }

    @GetMapping
    @ApiOperation("Retrieve by query ")
    public Page<TimeseriesDTO> query( TimeseriesQueryVO vO) {
        return timeseriesService.query(vO);
    }
}
