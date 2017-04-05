package io.github.TheBusyBiscuit.GitHubWebAPI4Java;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.github.TheBusyBiscuit.GitHubWebAPI4Java.annotations.GitHubAccessPoint;

public class GitHubBlob extends GitHubFile {
	
	public GitHubBlob(GitHubWebAPI api, GitHubRepository repo, String id) {
		super(api, repo, "/git/blobs/" + id);
	}
	
	public GitHubBlob(GitHubWebAPI api, GitHubRepository repo, String id, JsonElement response) {
		super(api, repo, "/git/blobs/" + id, response);
	}

	public GitHubBlob(GitHubObject obj) {
		super(obj);
	}
	
	@Override
	public String getRawURL() {
		return ".*repos/.*/.*/git/blobs/.*";
	}

	@Override
	@GitHubAccessPoint(path = "@size", type = Integer.class)
	public int getSize() throws IllegalAccessException {
		JsonElement element = getResponse(false);
		
		if (element == null) {
			throw new IllegalAccessException("Could not connect to '" + getURL() + "'");
		}
		JsonObject response = element.getAsJsonObject();

		return isInvalid(response, "size") ? null: response.get("size").getAsInt();
	}

	@GitHubAccessPoint(path = "@content", type = String.class)
	public String getFileContent() throws IllegalAccessException {
		JsonElement element = getResponse(true);
		
		if (element == null) {
			throw new IllegalAccessException("Could not connect to '" + getURL() + "'");
		}
		JsonObject response = element.getAsJsonObject();
		
		return isInvalid(response, "content") ? null: response.get("content").getAsString();
	}

	@GitHubAccessPoint(path = "@encoding", type = String.class)
	public String getEncoding() throws IllegalAccessException {
		JsonElement element = getResponse(true);
		
		if (element == null) {
			throw new IllegalAccessException("Could not connect to '" + getURL() + "'");
		}
		JsonObject response = element.getAsJsonObject();
		
		return isInvalid(response, "encoding") ? null: response.get("encoding").getAsString();
	}
}
