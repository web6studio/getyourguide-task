import { DEFAULT_LIMIT, ACTIVITIES_URL } from "@/constants";

export async function fetchActivitiesService(
  title: string = "",
  offset: number = 0,
  limit: number = DEFAULT_LIMIT,
  controller?: AbortController
): Promise<Activity[]> {
  const url = new URL(ACTIVITIES_URL);
  if (title) url.searchParams.append("title", title);
  url.searchParams.append("offset", offset.toString());
  url.searchParams.append("limit", limit.toString());

  const response = await fetch(url.toString(), {
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    signal: controller?.signal // Connect AbortController to request
  });

  if (!response.ok) throw new Error("Failed to fetch activities");
  return await response.json();
}

// Fetch single activity by ID
export async function fetchActivityService(id: number): Promise<Activity> {
  const response = await fetch(`${ACTIVITIES_URL}/${id}`, {
    method: "GET",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  });

  if (!response.ok) throw new Error(`Failed to fetch activity with ID: ${id}`);

  return await response.json();
}
