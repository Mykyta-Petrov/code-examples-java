package com.docusign.controller.admin.services;

import com.docusign.admin.api.ProductPermissionProfilesApi;
import com.docusign.admin.model.ProductPermissionProfileRequest;
import com.docusign.admin.model.UserProductPermissionProfilesRequest;
import com.docusign.admin.model.UserProductPermissionProfilesResponse;

import java.util.UUID;

public class UpdateUserProductPermissionProfileByEmail {
    public static UserProductPermissionProfilesResponse updateUserProductPermissionProfile(
		  ProductPermissionProfilesApi productPermissionProfilesApi,
		  String permissionProfileId,
		  UUID productId,
		  String emailAddress,
		  UUID organizationId,
		  UUID accountId
    ) throws Exception {
	// Step 3 start
	   var productPermissionProfileRequest = new ProductPermissionProfileRequest();
	   productPermissionProfileRequest.setPermissionProfileId(permissionProfileId);
	   productPermissionProfileRequest.setProductId(productId);

	   var userProductPermissionProfilesRequest = new UserProductPermissionProfilesRequest();
	   userProductPermissionProfilesRequest.email(emailAddress);
	   userProductPermissionProfilesRequest.addProductPermissionProfilesItem(productPermissionProfileRequest);
	// Step 3 end
	// Step 4 start
	   return productPermissionProfilesApi.addUserProductPermissionProfilesByEmail(
			 organizationId,
			 accountId,
			 userProductPermissionProfilesRequest);
	// Step 4 end
    }
}
