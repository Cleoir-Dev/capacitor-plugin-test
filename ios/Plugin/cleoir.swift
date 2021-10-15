import Foundation

@objc public class cleoir: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
