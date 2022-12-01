package fpt.capstone.vuondau.service;


import fpt.capstone.vuondau.entity.request.PanoRequest;
import fpt.capstone.vuondau.entity.response.GetPanoResponse;


import java.util.List;

public interface PanoService {


    GetPanoResponse createNewPano(PanoRequest panoRequest);



}
