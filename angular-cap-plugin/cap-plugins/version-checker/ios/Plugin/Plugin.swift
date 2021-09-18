import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
enum VersionError: Error {
    case invalidResponse, invalidBundleInfo
}

@objc(VersionChecker)
public class VersionChecker: CAPPlugin {

    @objc func isUpdateAvailable(_ call: CAPPluginCall) {
        guard let info = Bundle.main.infoDictionary,
                let bundleId = info["CFBundleIdentifier"] as? String else {
            return;
        }
        
        // let bundleId = call.getString("bundleId") ?? "" // App id is useless. We pick bundle id in iOS to query the verison
        print("[Plugin] Bundle ID: " + bundleId)
        
        _ = try? checkLiveVersion (bundleID: bundleId, completion: { (update, error) in
            if let error = error {
                print("[Plugin] Exception")
                print(error)
            } else if let update = update {
                print("[Plugin] Live version: " + update)
                call.success([
                    "liveVersion": update,
                ])
            }
        })
    }
    
    func checkLiveVersion (bundleID: String, completion: @escaping (String?, Error?) -> Void) throws -> URLSessionDataTask {
        let url = URL(string: "https://itunes.apple.com/lookup?bundleId=\(bundleID)")!
        print("[Plugin] https://itunes.apple.com/lookup?bundleId=\(bundleID)")
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
            do {
                if let error = error { throw error }
                guard let data = data else { throw VersionError.invalidResponse }
                let json = try JSONSerialization.jsonObject(with: data, options: [.allowFragments]) as? [String: Any]
                
//                if let JSONString = String(data: try JSONSerialization.data(withJSONObject: json), encoding: String.Encoding.utf8) {
//                    print("[Plugin] " + JSONString)
//                }
                
                guard let result = (json?["results"] as? [Any])?.first as? [String: Any], let version = result["version"] as? String else {
                    throw VersionError.invalidResponse
                }
                completion(version, nil)
            } catch {
                completion(nil, error)
            }
        }
        task.resume()
        return task
    }
}
