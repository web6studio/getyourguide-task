export interface Activity {
  id: number;
  title: string;
  price: number;
}

export async function fetchActivitiesService(): Promise<Activity[]> {
  const response = await fetch('http://localhost:8080/activities', {
    method: 'GET',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
    }
  });
  if (!response.ok) {
    throw new Error('Failed to fetch activities');
  }
  return await response.json();
}

export async function fetchActivityService(id: number): Promise<Activity> {
  const response = await fetch(`http://localhost:8080/activity/${id}`, {
    method: 'GET',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
    }
  });

  if (!response.ok) {
    throw new Error(`Failed to fetch activity with ID: ${id}`);
  }

  return await response.json();
}
