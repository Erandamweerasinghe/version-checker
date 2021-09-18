Creating Capacitor Plugins

step : 01  

	 npx @capacitor/cli@2.4.7 plugin:generate
	 Plugin NPM name (kebab-case): my-plugin
	 Plugin id (domain-style syntax. ex: com.example.plugin) com.ionicframework.myplugin
	 Plugin class name (ex: AwesomePlugin) MyPlugin
	 description:
	 git repository:
	 author:	
	 license: MIT
	 package.json will be created, do you want to continue? (Y/n)

step : 02 

	Implement your plugin

step : 03

	npm run build (withing plugin directry)
	
	for local testing -->> npm link (withing plugin derectry)
			       npm link plugin_name  (withing project directry)
			       npm install plugin_name (withing project directry)
step : 04  (if publish)

	publish your plugin
		npm publish

step : 05

	npm install ./cap-pluging/version-checker  (withing project derectry )

	After install plugin package.json file nnow show the plugin package
		"my-plugin": "file:my-plugin"


more details :  https://capacitorjs.com/docs/v2/plugins/creating-plugins
		
	
	
