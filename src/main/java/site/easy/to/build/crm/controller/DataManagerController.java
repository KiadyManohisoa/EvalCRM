package site.easy.to.build.crm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import site.easy.to.build.crm.config.entity.ListClean;
import site.easy.to.build.crm.entity.OAuthUser;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.google.service.acess.GoogleAccessService;
import site.easy.to.build.crm.service.data.CleanDataService;
import site.easy.to.build.crm.service.user.OAuthUserService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.AuthenticationUtils;


@RequestMapping("/data")
@Controller
public class DataManagerController {

    final GoogleAccessService googleAccessService;
    final UserService userService;
    final OAuthUserService oAuthUserService;
    final AuthenticationUtils authenticationUtils;
    final CleanDataService cleanDataService;

    public DataManagerController(GoogleAccessService googleAccessService, UserService userService, OAuthUserService oAuthUserService, AuthenticationUtils authenticationUtils, CleanDataService cleanDataService) {
        this.googleAccessService = googleAccessService;
        this.userService = userService;
        this.oAuthUserService = oAuthUserService;
        this.authenticationUtils = authenticationUtils;
        this.cleanDataService = cleanDataService;
    }

    @PostMapping("/clean")
    public String cleanData(RedirectAttributes redirectAttributes) {
        String redirection;
        String message = "";
        try {
            this.cleanDataService.cleanTables(ListClean.getEntities());
            message = "Success : Data cleaning succeeded";
            redirection = "redirect:/data/clean/form";
        }
        catch(Exception e) {
            e.printStackTrace();
            message = "Error : "+e.getMessage();
            redirection ="error/500";
        }
        redirectAttributes.addFlashAttribute("message", message);
        return redirection;
    }
    

    @GetMapping("/clean/form")
    public String getFormDataCleaner(@ModelAttribute("message") String message, Model model, Authentication authentication) throws Exception {
        model.addAttribute("message", message);
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            model.addAttribute("oAuthUser", null);
            return "manager/data-managing/form-clean-data";
        }

        int userId = authenticationUtils.getLoggedInUserId(authentication);
        User user = userService.findById(userId);
        OAuthUser oAuthUser = authenticationUtils.getOAuthUserFromAuthentication(authentication);

        List<String> scopesToCheck = Arrays.asList(
                GoogleAccessService.SCOPE_CALENDAR,
                GoogleAccessService.SCOPE_GMAIL,
                GoogleAccessService.SCOPE_DRIVE
        );

        googleAccessService.verifyAccessAndHandleRevokedToken(oAuthUser, user, scopesToCheck);
        oAuthUserService.save(oAuthUser, user);
        model.addAttribute("oAuthUser", oAuthUser);
        return "manager/data-managing/form-clean-data";
    }
    

}
