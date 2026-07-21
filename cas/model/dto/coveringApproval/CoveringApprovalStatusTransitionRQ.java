package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.security.CredentialsDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CoveringApprovalStatusTransitionRQ implements Serializable {

    private Integer covAppId;

    private String covAppRefNumber;

    private Integer assignUserID;

    private String assignADUserID;

    private String assignUser;

    private String customerName;

    private String branchName;

    private String solID;

    private String paperCreatedDate;

    private String lastComment;

    private String lastCommentedUser; //Senders Display name

    private String assignUserDisplayName; // Receivers Display Name

    private String action;

    private DomainConstants.CoveringApprovalStatus newStatus;

    private DomainConstants.FacilityPaperRoutingStatus routingStatus;

    private String userName;

    private List<String> toAddresses;

    private CredentialsDTO credentialsDTO;

    private String chequeNumber;

    private String tranAmount;

    private String accountNumber;

    public String getAction() {
        switch (this.getNewStatus()) {
            case IN_PROGRESS:
                this.action = " has been forwarded to you";
                break;

            case CANCEL:
                this.action = " has been returned to you";
                break;

            case REJECTED:
                this.action = " has been declined";
                break;

            case APPROVED:
                this.action = " has been forwarded to you";
                break;

            default:
                this.action = "";
        }
        return action;
    }

    public List<String> getToAddresses() {
        if (toAddresses == null) {
            toAddresses = new ArrayList<>();
        }
        return toAddresses;
    }
}
