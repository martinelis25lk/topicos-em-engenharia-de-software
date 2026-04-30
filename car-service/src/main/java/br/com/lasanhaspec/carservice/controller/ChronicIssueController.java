package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.dto.ChronicIssueDTO;
import br.com.lasanhaspec.carservice.dto.ChronicIssueDetailDTO;
import br.com.lasanhaspec.carservice.dto.VehicleChronicPageDTO;
import br.com.lasanhaspec.carservice.dto.VehicleChronicSummaryDTO;
import br.com.lasanhaspec.carservice.service.ChronicIssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/chronic-issues")
@RestController
public class ChronicIssueController {


    private final ChronicIssueService chronicIssueService;

    public ChronicIssueController(ChronicIssueService chronicIssueService){
        this.chronicIssueService = chronicIssueService;
    }





    // GET    /chronic-issues/models            todos os modelos com issue?
    @GetMapping("/models")
    public ResponseEntity<List<VehicleChronicSummaryDTO>> getAllModelsWithIssues(){
        return ResponseEntity.ok(chronicIssueService.getAllModelsWithIssues());

    }



    // GET    /chronic-issues/models/{id}       getModelChronicPage
    @GetMapping("/models/{id}")
    public ResponseEntity<VehicleChronicPageDTO> getModelChronicPage(@PathVariable("id") Long modelId){
        return ResponseEntity.ok(chronicIssueService.getModelChronicPage(modelId));
    }



    // GET    /chronic-issues/{id}              getIssueDetail - pagina de detalhe de 1 problema cronico
    @GetMapping("/{id}")
    public ResponseEntity<ChronicIssueDetailDTO> getIssueDetail(@PathVariable Long id){
        return ResponseEntity.ok(chronicIssueService.getIssueDetail(id));

    }


   // POST   /chronic-issues/{id}/occurrence   reportOccurrence
   @PostMapping("/{id}/occurrence")
   public ResponseEntity<Void> reportOccurrence(
           @PathVariable("id") Long issueId,
           @RequestParam Long vehicleId
   ) {
       chronicIssueService.reportOccurrence(issueId, vehicleId);
       return ResponseEntity.ok().build();
   }


    // POST   /chronic-issues                   createIssue
    @PostMapping("/create-issue")
    public ResponseEntity<Long> createIssue(@RequestBody  ChronicIssueDTO dto){
        return ResponseEntity.ok(chronicIssueService.createIssue(dto));

    }


    @PutMapping("/{id}")
    public ResponseEntity<ChronicIssueDetailDTO> updateIssue(@PathVariable Long id, @RequestBody ChronicIssueDTO dto){
        return ResponseEntity.ok(chronicIssueService.updateChronicIssue(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id){
        chronicIssueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approveIssue(@PathVariable Long id){
        chronicIssueService.approveChronicIssue(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectIssue(@PathVariable Long id){
        chronicIssueService.rejectChronicIssue(id);
        return ResponseEntity.noContent().build();
    }





}
