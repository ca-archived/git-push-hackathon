//
//  UserDefaults+Extensions.swift
//  GitMe
//
//  Created by 藤井陽介 on 2018/02/18.
//  Copyright © 2018 touyou. All rights reserved.
//

import UIKit

// MARK: - Image Extensions

extension UserDefaults {

    func set(_ image: UIImage, forKey key: String) {

        if let data = UIImagePNGRepresentation(image) {

            let filename = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)[0].appendingPathComponent(key + ".png")
            do {

                try data.write(to: filename)
                self.set(filename, forKey: key)
            } catch let error {

                print(error)
            }
        }
    }

    func image(forKey key: String) -> UIImage? {

        guard let filename = self.object(forKey: key) as? URL else {

            return nil
        }
        do {

            let data = try Data(contentsOf: filename)
            return UIImage(data: data)
        } catch let error {

            print(error)
            return nil
        }
    }
}
