package com.healthAppointment.healthAppointment.controller;

import com.healthAppointment.healthAppointment.model.Qualification;
import com.healthAppointment.healthAppointment.model.dto.QualificationDTO;
import com.healthAppointment.healthAppointment.model.dto.QualificationReducedDTO;
import com.healthAppointment.healthAppointment.service.IQualificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/qualification")
public class QualificationController {

    private final IQualificationService service;

    QualificationController(IQualificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody QualificationDTO request) {
        try {
            QualificationDTO response = service.save(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar Especialidade: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @PutMapping("/{specialityId}/add_type/{typeId}")
    public ResponseEntity<?> addType(@PathVariable("specialityId") String specialityId, @PathVariable("typeId") String typeId) {
        try {
            Qualification response = service.addType(specialityId, typeId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar Especialidade: " + e.getMessage()
                            .replace("java.lang.Exception: ", ""));
        }
    }

    @GetMapping("/list_by_types/{typeCode}")
    public List<ResponseEntity<?>> listByType(@PathVariable("typeCode") String typeCode) {
        try {
            List<QualificationReducedDTO> response = service.listByType(typeCode);
            return Collections.singletonList(new ResponseEntity<>(response, HttpStatus.OK));
        } catch (Exception e) {
            return Collections.singletonList(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao buscar Especialidade: " + e.getMessage()
                            .replace("java.lang.Exception: ", "")));
        }
    }

    @GetMapping
    public void preencher() {

        List<QualificationDTO> qualifications = new ArrayList<>();
//        var types = Collections.singletonList("225100000N");
        List<QualificationDTO> types = new ArrayList<>();
        types.add(new QualificationDTO("225100000N"));

//        qualifications.add(new QualificationDTO("Fisioterapia", "225100000N", "Fisioterapeutas são profissionais de saúde que avaliam e tratam pessoas com problemas de saúde resultantes de lesões ou doenças. Os PTs avaliam o movimento articular, a força e resistência muscular, a função do coração e dos pulmões e o desempenho das atividades necessárias na vida diária, entre outras responsabilidades. O tratamento inclui exercícios terapêuticos, treinamento de resistência cardiovascular e treinamento em atividades da vida diária. (2) Um fisioterapeuta é uma pessoa qualificada por um programa credenciado em fisioterapia, licenciado pelo estado e praticando dentro do escopo dessa licença. Os fisioterapeutas tratam doenças, lesões ou perda de uma parte do corpo por meios físicos, como aplicação de luz, calor, frio, água, eletricidade, massagem e exercícios. Eles desenvolvem planos de tratamento com base nos pontos fortes e fracos de cada paciente, amplitude de movimento e capacidade de funcionamento. (3) Um profissional de saúde especializado em fisioterapia - o campo da saúde preocupado principalmente com o tratamento de distúrbios com agentes e métodos físicos, como massagem, manipulação, exercícios terapêuticos, frio, calor (incluindo ondas curtas, micro-ondas e diatermia ultrassônica), hidroterapia, estimulação elétrica e luz para auxiliar na reabilitação de pacientes e na restauração da função normal após uma doença ou lesão.", null));

//        qualifications.add(new QualificationDTO("Gestão de caso", "2251C0400N", null, types));
//        qualifications.add(new QualificationDTO("cardiopulmonar", "2251C2600N", null, types));
//        qualifications.add(new QualificationDTO("Ergonomia", "2251E1200N", null, types));
//        qualifications.add(new QualificationDTO("Eletrofisiologia Clínica", "2251E1300N", null, types));
//        qualifications.add(new QualificationDTO("Geriatria", "2251G0304N", null, types));
//        qualifications.add(new QualificationDTO("Mão", "2251H1200N", null, types));
//        qualifications.add(new QualificationDTO("Fatores humanos", "2251H1300N", null, types));
//        qualifications.add(new QualificationDTO("Neurologia", "2251N0400N", null, types));
//        qualifications.add(new QualificationDTO("Pediatria", "2251P0200N", null, types));
//        qualifications.add(new QualificationDTO("Esportes", "2251S0007N", null, types));
//        qualifications.add(new QualificationDTO("Ortopédico", "2251X0800N", null, types));
//        qualifications.add(new QualificationDTO("RPG (Reeducação Postural Global)", "50000446", "RPG visa o equilíbrio entre as forças musculares que compõem o corpo humano, devolvendo a capacidade de movimentação normal das articulações e a manutenção de uma boa postura, por meio de alongamentos, tração e respiração.", types));
//        qualifications.add(new QualificationDTO("Pilates - Individual", "50000853", "Método de condicionamento físico composto por exercícios físicos e alongamentos que utilizam o peso do próprio corpo na sua execução.", types));
//        qualifications.add(new QualificationDTO("Pilates - Grupo", "50000861", "Método de condicionamento físico composto por exercícios físicos e alongamentos que utilizam o peso do próprio corpo na sua execução.", types));
//        qualifications.add(new QualificationDTO("Acumpultura / Auriculoterapia", "31601014", "Acupuntura - Terapia milenar originária da China, que consiste na aplicação de agulhas em pontos específicos do corpo para tratar doenças e para promover saúde. / Auriculoterapia - Técnica que consiste na estimulação mecânica de pontos específicos do pavilhão auricular.", types));
//        qualifications.add(new QualificationDTO("Consulta domiciliar em fisioterapia", "50000241", null, types));


        for (QualificationDTO qualificationDTO: qualifications) {
            service.save(qualificationDTO);
        }
    }
}
