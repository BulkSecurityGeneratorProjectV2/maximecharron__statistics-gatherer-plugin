package org.jenkins.plugins.statistics.gatherer;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Created by hthakkallapally on 6/25/2015.
 */
@Extension
public class StatisticsConfiguration extends GlobalConfiguration {

    private static final String SLASH = "/";
    public static final String PROTOCOL_ERROR_MESSAGE = "Only http and https protocols are supported";

    private String queueUrl;
    private String buildUrl;
    private String projectUrl;
    private String scmCheckoutUrl;
    private String buildStepUrl;

    private Boolean queueInfo;
    private Boolean buildInfo;
    private Boolean projectInfo;
    private Boolean scmCheckoutInfo;
    private Boolean buildStepInfo;

    public StatisticsConfiguration() {
        load();
    }

    public static StatisticsConfiguration get() {
        return GlobalConfiguration.all().get(StatisticsConfiguration.class);
    }

    public String getQueueUrl() {
        if (queueUrl != null && !queueUrl.isEmpty()) {
            if (queueUrl.endsWith(SLASH)) {
                return queueUrl;
            }
            return queueUrl + SLASH;
        }
        return queueUrl;
    }

    public Boolean getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(Boolean projectInfo) {
        this.projectInfo = projectInfo;
        save();
    }

    public Boolean getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(Boolean buildInfo) {
        this.buildInfo = buildInfo;
        save();
    }

    public Boolean getQueueInfo() {
        return queueInfo;
    }

    public void setQueueInfo(Boolean queueInfo) {
        this.queueInfo = queueInfo;
        save();
    }


    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
        save();
    }

    public String getBuildUrl() {
        if (buildUrl != null && !buildUrl.isEmpty()) {
            if (buildUrl.endsWith(SLASH)) {
                return buildUrl;
            }
            return buildUrl + SLASH;
        }
        return buildUrl;
    }

    public void setBuildUrl(String buildUrl) {
        this.buildUrl = buildUrl;
        save();
    }

    public String getProjectUrl() {
        if (projectUrl != null && !projectUrl.isEmpty()) {
            if (projectUrl.endsWith(SLASH)) {
                return projectUrl;
            }
            return projectUrl + SLASH;
        }
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
        save();
    }

    public String getBuildStepUrl() {
        if (buildStepUrl != null && !buildStepUrl.isEmpty()) {
            if (buildStepUrl.endsWith(SLASH)) {
                return buildStepUrl;
            }
            return buildStepUrl + SLASH;
        }
        return buildStepUrl;
    }

    public void setBuildStepUrl(String buildStepUrl) {
        this.buildStepUrl = buildStepUrl;
        save();
    }

    public String getScmCheckoutUrl() {
        if (scmCheckoutUrl != null && !scmCheckoutUrl.isEmpty()) {
            if (scmCheckoutUrl.endsWith(SLASH)) {
                return scmCheckoutUrl;
            }
            return scmCheckoutUrl + SLASH;
        }
        return scmCheckoutUrl;
    }

    public void setScmCheckoutUrl(String scmCheckoutUrl) {
        this.scmCheckoutUrl = scmCheckoutUrl;
        save();
    }

    @Override
    public boolean configure(StaplerRequest request, JSONObject json) throws FormException {
        request.bindJSON(this, json);
        return true;
    }

    /**
     * Validate notificationUrl is filled correctly.
     *
     * @param buildUrl
     * @return
     */
    public FormValidation doCheckBuildUrl(
            @QueryParameter("buildUrl") final String buildUrl) {
        if (buildUrl == null || buildUrl.isEmpty()) {
            return FormValidation.error("Provide valid Build URL. " +
                    "For ex: \"http://ci.mycompany.com/api/builds\"");
        }
        if (validateProtocolUsed(buildUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueUrl(
            @QueryParameter("queueUrl") final String queueUrl) {
        if (queueUrl == null || queueUrl.isEmpty()) {
            return FormValidation.error("Provide valid Queue URL. " +
                    "For ex: \"http://ci.mycompany.com/api/queues\"");
        }
        if (validateProtocolUsed(queueUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectUrl(
            @QueryParameter("projectUrl") final String projectUrl) {
        if (projectUrl == null || projectUrl.isEmpty()) {
            return FormValidation.error("Provide valid Project URL. " +
                    "For ex: \"http://ci.mycompany.com/api/\"");
        }
        if (validateProtocolUsed(projectUrl))
            return FormValidation.error(PROTOCOL_ERROR_MESSAGE);
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildInfo(
            @QueryParameter("buildInfo") final Boolean buildInfo) {
        if (buildInfo == null) {
            return FormValidation.error("Provide valid Build Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckQueueInfo(
            @QueryParameter("queueInfo") final Boolean queueInfo) {
        if (queueInfo == null) {
            return FormValidation.error("Provide valid Queue Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckProjectInfo(
            @QueryParameter("projectInfo") final Boolean projectInfo) {
        if (projectInfo == null) {
            return FormValidation.error("Provide valid Project Info. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckScmCheckoutInfo(
            @QueryParameter("scmCheckoutInfo") final Boolean scmCheckoutInfo) {
        if (scmCheckoutInfo == null) {
            return FormValidation.error("Provide valid ScmCheckoutInfo. ");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckBuildStepInfo(
            @QueryParameter("buildStepInfo") final Boolean buildStepInfo) {
        if (buildStepInfo == null) {
            return FormValidation.error("Provide valid BuildStepInfo. ");
        }
        return FormValidation.ok();
    }

    private boolean validateProtocolUsed(String url) {
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            return true;
        }
        return false;
    }
}
