package com.leatherback.meawallet.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Utility.WrapOneTimeSessionKeyUtility;
import ViewModel.DecryptMeaWalletResponseViewModel;
import ViewModel.WrapOneTimeSessionKeyRequestViewModel;
import ViewModel.WrapOneTimeSessionKeyResponseViewModel;

@RestController
public class WrapOneTimeSessionKeyController {

    @PostMapping("/warptoken")
    public WrapOneTimeSessionKeyResponseViewModel Warptoken(@RequestBody WrapOneTimeSessionKeyRequestViewModel request){
       
        WrapOneTimeSessionKeyResponseViewModel response = new WrapOneTimeSessionKeyResponseViewModel();
        try {
            WrapOneTimeSessionKeyUtility Wrap = new WrapOneTimeSessionKeyUtility();
            response = Wrap.EncrytOneTimeToken(request.Modulus, request.Exponent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/decrypt")
    public WrapOneTimeSessionKeyResponseViewModel Decrypt(@RequestBody DecryptMeaWalletResponseViewModel request){
       
        WrapOneTimeSessionKeyResponseViewModel response = new WrapOneTimeSessionKeyResponseViewModel();
        try {
            WrapOneTimeSessionKeyUtility Wrap = new WrapOneTimeSessionKeyUtility();
            response = Wrap.DecryptMeaWalletResponse(request.MeaWalletResponseData, request.OneTimeSessionKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
