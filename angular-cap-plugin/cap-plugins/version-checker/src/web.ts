

import { WebPlugin } from '@capacitor/core';
import { VersionCheckerPlugin } from './definitions';

export class VersionCheckerWeb extends WebPlugin implements VersionCheckerPlugin {
	constructor() {
		super({
			name: 'VersionChecker',
			platforms: ['web'],
		});
	}

	async isUpdateAvailable(options: { bundleId: string }): Promise<{ liveVersion: string }> {
		return new Promise(res => {
			res({ liveVersion: String(options)});
		});
	}
}

const VersionChecker = new VersionCheckerWeb();

export { VersionChecker };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(VersionChecker);
