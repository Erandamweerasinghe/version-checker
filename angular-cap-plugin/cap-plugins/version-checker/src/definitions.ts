
declare module '@capacitor/core' {
interface PluginRegistry {
	VersionChecker: VersionCheckerPlugin;
}
}

export interface VersionCheckerPlugin {
	isUpdateAvailable(options: { bundleId: string}): Promise<{ liveVersion: string }>;
}
