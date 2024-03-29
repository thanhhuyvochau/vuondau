package fpt.capstone.vuondau.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.entity.request.RoleRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.RoleResponse;

import java.util.List;

public interface IRoleService {

    List<RoleResponse> getAll();

    RoleResponse create(RoleRequest roleRequest);

    RoleResponse update(RoleRequest roleRequest, Long id);

    Boolean delete(Long id);


}
