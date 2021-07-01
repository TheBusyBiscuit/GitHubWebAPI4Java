package io.github.TheBusyBiscuit.GitHubWebAPI4Java.objects.repositories;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.TheBusyBiscuit.GitHubWebAPI4Java.GitHubWebAPI;
import io.github.TheBusyBiscuit.GitHubWebAPI4Java.annotations.GitHubAccessPoint;
import io.github.TheBusyBiscuit.GitHubWebAPI4Java.objects.GitHubObject;
import io.github.TheBusyBiscuit.GitHubWebAPI4Java.objects.RepositoryFeature;
import io.github.TheBusyBiscuit.GitHubWebAPI4Java.objects.users.GitHubUser;

public class GitHubMilestone extends RepositoryFeature {

    GitHubRepository repo;

    public GitHubMilestone(GitHubWebAPI api, GitHubRepository repo, int id) {
        super(api, repo, "/milestones/" + id);

        this.repo = repo;
    }

    public GitHubMilestone(GitHubWebAPI api, GitHubRepository repo, int id, JsonElement response) {
        super(api, repo, "/milestones/" + id);

        this.repo = repo;
        this.minimal = response;
    }

    public GitHubMilestone(GitHubObject obj) {
        super(obj);
    }

    public GitHubRepository getRepository() {
        return this.repo;
    }

    @Override
    public String getRawURL() {
        return ".*repos/.*/.*/milestones/.*";
    }

    @GitHubAccessPoint(path = "@creator", type = GitHubUser.class, requiresAccessToken = false)
    public GitHubUser getCreator() throws IllegalAccessException {
        JsonElement element = getResponse(false);

        if (element == null) {
            throw new IllegalAccessException("Could not connect to '" + getURL() + "'");
        }
        JsonObject response = element.getAsJsonObject();

        return isInvalid(response, "creator") ? null : new GitHubUser(api, response.get("creator").getAsJsonObject().get("login").getAsString(), response.get("creator").getAsJsonObject());
    }

    @GitHubAccessPoint(path = "@description", type = String.class, requiresAccessToken = false)
    public String getDescription() throws IllegalAccessException {
        return getString("description", false);
    }

    @GitHubAccessPoint(path = "/labels", type = GitHubLabel.class, requiresAccessToken = false)
    public List<GitHubLabel> getLabels() throws IllegalAccessException, UnsupportedEncodingException {
        GitHubObject repos = new GitHubObject(api, this, "/labels");
        JsonElement response = repos.getResponse(true);

        if (response == null) {
            throw new IllegalAccessException("Could not connect to '" + getURL() + "'");
        }

        List<GitHubLabel> list = new ArrayList<>();
        JsonArray array = response.getAsJsonArray();

        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();

            GitHubLabel label = new GitHubLabel(api, getRepository(), object.get("name").getAsString(), object);
            list.add(label);
        }

        return list;
    }

    @GitHubAccessPoint(path = "@open_issues", type = Integer.class, requiresAccessToken = false)
    public Integer getOpenIssuesAmount() throws IllegalAccessException {
        return getInteger("open_issues", false);
    }

    @GitHubAccessPoint(path = "@closed_issues", type = Integer.class, requiresAccessToken = false)
    public Integer getClosedIssuesAmount() throws IllegalAccessException {
        return getInteger("closed_issues", false);
    }

    @GitHubAccessPoint(path = "@due_on", type = Date.class, requiresAccessToken = false)
    public Date getDueDate() throws IllegalAccessException {
        return getDate("due_on", false);
    }
}
