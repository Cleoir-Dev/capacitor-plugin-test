export interface cleoirPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
