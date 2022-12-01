package fpt.capstone.vuondau.service.Impl;

import fpt.capstone.vuondau.entity.Pano;
import fpt.capstone.vuondau.entity.Resource;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.entity.dto.ResourceDto;
import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.GetPanoResponse;
import fpt.capstone.vuondau.repository.PanoRepository;
import fpt.capstone.vuondau.repository.ResourceRepository;
import fpt.capstone.vuondau.service.PanoService;
import fpt.capstone.vuondau.util.MessageUtil;
import fpt.capstone.vuondau.util.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Service
@Transactional
public class PanoServiceImpl implements PanoService {

    private final ResourceRepository resourceRepository;

    private final MessageUtil messageUtil;

    private final PanoRepository panoRepository ;

    public PanoServiceImpl(ResourceRepository resourceRepository, MessageUtil messageUtil, PanoRepository panoRepository) {
        this.resourceRepository = resourceRepository;
        this.messageUtil = messageUtil;
        this.panoRepository = panoRepository;
    }

    @Override
    public GetPanoResponse createNewPano(PanoRequest panoRequest) {
        Pano pano = new Pano();
        pano.setName(panoRequest.getName());
        pano.setPublishDate(panoRequest.getPublishDate());
        pano.setExpirationDate(panoRequest.getExpirationDate());


        pano.setLinkUrl(panoRequest.getLinkUrl());

        Resource resource = resourceRepository.findById(panoRequest.getResourceId())
                .orElseThrow(() -> ApiException.create(HttpStatus.NOT_FOUND).withMessage(messageUtil.getLocalMessage("khong tim thay anh") + panoRequest.getResourceId()));
        pano.setResource(resource);


        pano.setVisible(panoRequest.getVisible());


        Pano panoSaved = panoRepository.save(pano);


        return convertPanoToPanoResponse(panoSaved);
    }

    public static GetPanoResponse convertPanoToPanoResponse(Pano pano) {
        GetPanoResponse getPanoResponse = new GetPanoResponse();
        getPanoResponse.setId(pano.getId());
        getPanoResponse.setName(pano.getName());
        getPanoResponse.setPublishDate(pano.getPublishDate());
        getPanoResponse.setExpirationDate(pano.getExpirationDate());

        getPanoResponse.setResource(ObjectUtil.copyProperties(pano.getResource(), new ResourceDto(), ResourceDto.class));
        getPanoResponse.setVisible(pano.getVisible());
        getPanoResponse.setLinkUrl(pano.getLinkUrl());
        return getPanoResponse;
    }
}
