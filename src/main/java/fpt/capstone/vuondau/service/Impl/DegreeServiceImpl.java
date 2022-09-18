package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Degree;
import fpt.capstone.vuondau.entity.Grade;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.request.DegreeRequest;
import fpt.capstone.vuondau.entity.response.DegreeResponse;
import fpt.capstone.vuondau.entity.response.GradeResponse;
import fpt.capstone.vuondau.repository.DegreeRepository;
import fpt.capstone.vuondau.service.IDegreeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DegreeServiceImpl implements IDegreeService {
private final DegreeRepository degreeRepository ;

    public DegreeServiceImpl(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    @Override
    public DegreeResponse createNewDegree(DegreeRequest degreeRequest) {
        Degree degree = new Degree() ;
        degree.setCode(degreeRequest.getCode());
        degree.setName(degreeRequest.getName());



        Degree degreeSaved = degreeRepository.save(degree);
        DegreeResponse response = new DegreeResponse();
        response.setId(degreeSaved.getId());
        response.setName(degreeSaved.getName());
        response.setCode(degreeSaved.getCode());

        return response;
    }

    @Override
    public DegreeResponse updateDegree(Long degreeId, DegreeRequest degreeRequest) {

        Degree degree = degreeRepository.findById(degreeId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay degree"));
        degree.setCode(degreeRequest.getCode());
        degree.setName(degreeRequest.getName());
        Degree degreeSaved = degreeRepository.save(degree);
        DegreeResponse response = new DegreeResponse();
        response.setId(degreeSaved.getId());
        response.setName(degreeSaved.getName());
        response.setCode(degreeSaved.getCode());

        return response;

    }

    @Override
    public Long deleteDegree(Long degreeId) {
        Degree degree = degreeRepository.findById(degreeId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay degree"));
        degreeRepository.delete(degree);
        return  degreeId ;
    }

    @Override
    public DegreeResponse getDegree(Long degreeId) {
        Degree degree = degreeRepository.findById(degreeId).orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage("Khong tim thay degree"));
        DegreeResponse response = new DegreeResponse();
        response.setId(degree.getId());
        response.setName(degree.getName());
        response.setCode(degree.getCode());
        return response;
    }
}
