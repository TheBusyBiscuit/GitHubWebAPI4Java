package io.github.TheBusyBiscuit.GitHubWebAPI4Java;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.TheBusyBiscuit.GitHubWebAPI4Java.annotations.GitHubAccessPoint;

public class GitHubFile extends GitHubObject {
	
	protected GitHubRepository repo;
	
	public GitHubFile(GitHubWebAPI api, GitHubRepository repo, String suffix) {
		super(api, repo, suffix);
		
		this.repo = repo;
	}
	
	public GitHubFile(GitHubWebAPI api, GitHubRepository repo, String suffix, JsonElement response) {
		super(api, repo, suffix);
		
		this.repo = repo;
		this.minimal = response;
	}

	public GitHubFile(GitHubObject obj) {
		super(obj);
	}
	
	public GitHubRepository getRepository() {
		return this.repo;
	}

	@GitHubAccessPoint(path = "@sha", type = String.class)
	public String getID() throws IllegalAccessException {
		JsonElement element = getResponse(false);
		
		if (element == null) {
			throw new IllegalAccessException("Could not connect to '" + getURL() + "'");
		}
		JsonObject response = element.getAsJsonObject();
		
		return isInvalid(response, "sha") ? null: response.get("sha").getAsString();
	}

	@GitHubAccessPoint(path = "@path", type = String.class)
	public String getFile() throws IllegalAccessException {
		JsonElement element = getResponse(false);
		
		if (element == null) {
			return null;
		}
		
		JsonObject response = element.getAsJsonObject();
		
		return isInvalid(response, "path") ? null: response.get("path").getAsString();
	}

	@GitHubAccessPoint(path = "@type", type = String.class)
	public String getType() throws IllegalAccessException {
		JsonElement element = getResponse(false);
		
		if (element == null) {
			return null;
		}
		
		JsonObject response = element.getAsJsonObject();
		
		return isInvalid(response, "type") ? null: response.get("type").getAsString();
	}
	
	@GitHubAccessPoint(path = "@size", type = Integer.class)
	public int getSize() throws IllegalAccessException {
		return 0;
	}
}
